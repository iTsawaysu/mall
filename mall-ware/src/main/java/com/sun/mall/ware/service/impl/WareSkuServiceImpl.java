package com.sun.mall.ware.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.mall.common.utils.PageUtils;
import com.sun.mall.common.utils.Query;
import com.sun.mall.ware.dao.WareSkuDao;
import com.sun.mall.ware.entity.WareSkuEntity;
import com.sun.mall.ware.service.WareSkuService;
import org.springframework.stereotype.Service;

import java.util.Map;


/**
 * @author sun
 */
@Service("wareSkuService")
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {

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

}
