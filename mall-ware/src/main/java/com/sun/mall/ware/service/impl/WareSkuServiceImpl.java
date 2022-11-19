package com.sun.mall.ware.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.mall.common.utils.PageUtils;
import com.sun.mall.common.utils.Query;
import com.sun.mall.common.utils.R;
import com.sun.mall.ware.dao.WareSkuDao;
import com.sun.mall.ware.entity.WareSkuEntity;
import com.sun.mall.ware.feign.ProductFeignService;
import com.sun.mall.ware.service.WareSkuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * @author sun
 */
@Service("wareSkuService")
@Slf4j
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {

    @Resource
    private ProductFeignService productFeignService;

    @Override
    public PageUtils queryWareSkuPageByParams(Map<String, Object> params) {
        LambdaQueryWrapper<WareSkuEntity> wrapper = new LambdaQueryWrapper<>();

        String wareId = (String) params.get("wareId");
        if (StrUtil.isNotBlank(wareId) && !"0".equalsIgnoreCase(wareId)) {
            wrapper.eq(WareSkuEntity::getWareId, wareId);
        }

        String skuId = (String) params.get("skuId");
        if (StrUtil.isNotBlank(skuId) && !"0".equalsIgnoreCase(skuId)) {
            wrapper.eq(WareSkuEntity::getSkuId, skuId);
        }

        IPage<WareSkuEntity> page = this.page(new Query<WareSkuEntity>().getPage(params), wrapper);
        return new PageUtils(page);
    }

    @Override
    public void addStock(Long skuId, Long wareId, Integer skuNum) {
        List<WareSkuEntity> wareSkuList = lambdaQuery().eq(WareSkuEntity::getSkuId, skuId).eq(WareSkuEntity::getWareId, wareId).list();

        // 如果没有这个库存记录，则新增；有则更新库存
        if (wareSkuList.isEmpty()) {
            WareSkuEntity wareSku = new WareSkuEntity();
            wareSku.setWareId(wareId);
            wareSku.setSkuId(skuId);
            wareSku.setStock(skuNum);
            wareSku.setStockLocked(0);
            // 远程查询获取 SkuName
            try {
                R info = productFeignService.info(skuId);
                Map<String, Object> skuInfo = (Map<String, Object>) info.get("skuInfo");
                if (info.getCode() == 0) {
                    wareSku.setSkuName((String) skuInfo.get("skuName"));
                }
            } catch (Exception e) {
                log.error("error: {}", e);
            }
            this.save(wareSku);
        } else {
            this.getBaseMapper().updateStock(skuId, wareId, skuNum);
        }
    }

}
