package com.sun.mall.product.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.mall.common.utils.PageUtils;
import com.sun.mall.common.utils.Query;
import com.sun.mall.product.dao.SkuInfoDao;
import com.sun.mall.product.entity.SkuInfoEntity;
import com.sun.mall.product.service.SkuInfoService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;


/**
 * @author sun
 */
@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfoEntity> implements SkuInfoService {

    @Override
    public PageUtils querySkuInfoPageByParams(Map<String, Object> params) {
        LambdaQueryWrapper<SkuInfoEntity> wrapper = new LambdaQueryWrapper<>();

        String catelogId = (String) params.get("catelogId");
        if (StrUtil.isNotBlank(catelogId) && !"0".equalsIgnoreCase(catelogId)) {
            wrapper.eq(SkuInfoEntity::getCatalogId, catelogId);
        }

        String brandId = (String) params.get("brandId");
        if (StrUtil.isNotBlank(brandId) && !"0".equalsIgnoreCase(brandId)) {
            wrapper.eq(SkuInfoEntity::getBrandId, brandId);
        }

        String min = (String) params.get("min");
        if (StrUtil.isNotBlank(min)) {
            wrapper.ge(SkuInfoEntity::getPrice, min);
        }

        String max = (String) params.get("max");
        if (StrUtil.isNotBlank(max) && new BigDecimal(max).compareTo(BigDecimal.ZERO) == 1) {
            wrapper.le(SkuInfoEntity::getPrice, max);
        }

        String key = (String) params.get("key");
        if (StrUtil.isNotBlank(key)) {
            wrapper.and(w -> {
                w.eq(SkuInfoEntity::getSkuId, key)
                        .or()
                        .like(SkuInfoEntity::getSkuName, key);
            });
        }

        IPage page = this.page(new Query().getPage(params), wrapper);
        return new PageUtils(page);
    }
}
