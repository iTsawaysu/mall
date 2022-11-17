package com.sun.mall.product.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.mall.common.dto.SkuReductionDTO;
import com.sun.mall.common.dto.SpuBoundsDTO;
import com.sun.mall.common.utils.PageUtils;
import com.sun.mall.common.utils.Query;
import com.sun.mall.common.utils.R;
import com.sun.mall.product.dao.SpuInfoDao;
import com.sun.mall.product.entity.*;
import com.sun.mall.product.feign.CouponFeignService;
import com.sun.mall.product.service.*;
import com.sun.mall.product.vo.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {

    @Resource
    private SpuInfoDescService spuInfoDescService;

    @Resource
    private SpuImagesService spuImagesService;

    @Resource
    private ProductAttrValueService productAttrValueService;

    @Resource
    private AttrService attrService;

    @Resource
    private SkuInfoService skuInfoService;

    @Resource
    private SkuImagesService skuImagesService;

    @Resource
    private SkuSaleAttrValueService skuSaleAttrValueService;

    @Resource
    private CouponFeignService couponFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<SpuInfoEntity>()
        );
        return new PageUtils(page);
    }

    @Override
    public void saveSpuInfo(SpuSaveVo spuSaveVo) {
        // 1. 保存 Spu 的基本信息 - `pms_spu_info`；
        SpuInfoEntity spuInfoEntity = new SpuInfoEntity();
        BeanUtil.copyProperties(spuSaveVo, spuInfoEntity);
        spuInfoEntity.setCreateTime(new Date());
        spuInfoEntity.setUpdateTime(new Date());
        this.save(spuInfoEntity);

        // 2. 保存 Spu 的描述信息 - `pms_spu_info_desc`；
        List<String> descList = spuSaveVo.getDecript();
        SpuInfoDescEntity spuInfoDescEntity = new SpuInfoDescEntity();
        spuInfoDescEntity.setSpuId(spuInfoEntity.getId());
        spuInfoDescEntity.setDecript(StrUtil.join(",", descList));
        spuInfoDescService.save(spuInfoDescEntity);

        // 3. 保存 Spu 的图片集 - `pms_spu_images`；
        List<String> images = spuSaveVo.getImages();
        if (CollectionUtil.isEmpty(images) || ObjectUtil.isNull(images)) {
            List<SpuImagesEntity> spuImagesList = images.stream()
                    .map(image -> {
                        SpuImagesEntity spuImagesEntity = new SpuImagesEntity();
                        spuImagesEntity.setSpuId(spuInfoEntity.getId());
                        spuImagesEntity.setImgUrl(image);
                        return spuImagesEntity;
                    })
                    .collect(Collectors.toList());
            spuImagesService.saveBatch(spuImagesList);
        }

        // 4. 保存 Spu 的规格参数 - `pms_product_attr_value`；
        List<BaseAttrs> baseAttrsList = spuSaveVo.getBaseAttrs();
        List<ProductAttrValueEntity> productAttrValueList = baseAttrsList.stream()
                .map(baseAttr -> {
                    ProductAttrValueEntity productAttrValue = new ProductAttrValueEntity();
                    productAttrValue.setAttrId(baseAttr.getAttrId());
                    productAttrValue.setAttrName(attrService.getById(baseAttr.getAttrId()).getAttrName());
                    productAttrValue.setAttrValue(baseAttr.getAttrValues());
                    productAttrValue.setQuickShow(baseAttr.getShowDesc());
                    productAttrValue.setSpuId(spuInfoEntity.getId());
                    return productAttrValue;
                }).collect(Collectors.toList());
        productAttrValueService.saveBatch(productAttrValueList);

        // 5. 保存 Spu 的积分信息 - `mall_sms.sms_spu_bounds`；
        Bounds bounds = spuSaveVo.getBounds();
        SpuBoundsDTO spuBoundsDTO = new SpuBoundsDTO();
        BeanUtil.copyProperties(bounds, spuBoundsDTO);
        spuBoundsDTO.setSpuId(spuInfoEntity.getId());
        R rpcResult = couponFeignService.save(spuBoundsDTO);
        if (!rpcResult.getCode().equals(0)) {
            log.error("远程保存 Spu 的积分信息失败");
        }

        // 6. 保存 Spu 对应的 Sku 信息。
        List<Skus> skusList = spuSaveVo.getSkus();
        if (!CollectionUtil.isEmpty(skusList) && ObjectUtil.isNotNull(skusList)) {
            skusList.forEach(skus -> {
                // 找到默认图片（SkuDefaultImg 为 1）
                String defaultImage = "";
                for (Images image : skus.getImages()) {
                    if (image.getDefaultImg().equals(1)) {
                        defaultImage = image.getImgUrl();
                    }
                }

                // 6.1 Sku 的基本信息 - `pms_sku_info`；
                SkuInfoEntity skuInfoEntity = new SkuInfoEntity();
                BeanUtil.copyProperties(skus, skuInfoEntity);
                skuInfoEntity.setSpuId(spuInfoEntity.getId());
                skuInfoEntity.setBrandId(spuInfoEntity.getBrandId());
                skuInfoEntity.setCatalogId(spuInfoEntity.getCatalogId());
                skuInfoEntity.setSaleCount(0L);
                skuInfoEntity.setSkuDefaultImg(defaultImage);
                skuInfoService.save(skuInfoEntity);

                // 6.2 Sku 的图片信息 - `pms_sku_images`；
                List<SkuImagesEntity> skuImagesList = skus.getImages()
                        .stream()
                        .map(image -> {
                            SkuImagesEntity skuImagesEntity = new SkuImagesEntity();
                            skuImagesEntity.setSkuId(skuInfoEntity.getSkuId());
                            skuImagesEntity.setImgUrl(image.getImgUrl());
                            skuImagesEntity.setDefaultImg(image.getDefaultImg());
                            return skuImagesEntity;
                        })
                        .collect(Collectors.toList());
                skuImagesService.saveBatch(skuImagesList);

                // 6.3 Sku 的销售属性信息 - `pms_sku_sale_attr_value`；
                List<Attr> attrList = skus.getAttr();
                List<SkuSaleAttrValueEntity> skuSaleAttrValueList = attrList.stream()
                        .map(attr -> {
                            SkuSaleAttrValueEntity skuSaleAttrValueEntity = new SkuSaleAttrValueEntity();
                            BeanUtil.copyProperties(attr, skuSaleAttrValueEntity);
                            skuSaleAttrValueEntity.setSkuId(skuInfoEntity.getSkuId());
                            return skuSaleAttrValueEntity;
                        })
                        .collect(Collectors.toList());
                skuSaleAttrValueService.saveBatch(skuSaleAttrValueList);

                // 6.4 Sku 的优惠、满减、会员信息 - `mall_sms.sms_sku_ladder / sms_sku_full_reduction / sms_member_price`。
                SkuReductionDTO skuReductionDTO = new SkuReductionDTO();
                BeanUtil.copyProperties(skus, skuReductionDTO);
                skuReductionDTO.setSkuId(skuInfoEntity.getSkuId());
                R anotherRpcResult = couponFeignService.saveSkuReduction(skuReductionDTO);
                if (!anotherRpcResult.getCode().equals(0)) {
                    log.error("远程保存 Sku 优惠信息失败");
                }
            });
        }
    }

}
