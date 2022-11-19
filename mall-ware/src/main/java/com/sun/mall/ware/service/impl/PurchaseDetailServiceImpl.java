package com.sun.mall.ware.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.mall.common.utils.PageUtils;
import com.sun.mall.common.utils.Query;
import com.sun.mall.ware.dao.PurchaseDetailDao;
import com.sun.mall.ware.entity.PurchaseDetailEntity;
import com.sun.mall.ware.service.PurchaseDetailService;
import org.springframework.stereotype.Service;

import java.util.Map;


/**
 * @author sun
 */
@Service("purchaseDetailService")
public class PurchaseDetailServiceImpl extends ServiceImpl<PurchaseDetailDao, PurchaseDetailEntity> implements PurchaseDetailService {

    @Override
    public PageUtils queryPurchaseDetailPageByParams(Map<String, Object> params) {
        LambdaQueryWrapper<PurchaseDetailEntity> wrapper = new LambdaQueryWrapper<>();

        String status = (String) params.get("status");
        if (StrUtil.isNotBlank(status)) {
            wrapper.eq(PurchaseDetailEntity::getStatus, status);
        }

        String wareId = (String) params.get("wareId");
        if (StrUtil.isNotBlank(wareId) && !"0".equalsIgnoreCase(wareId)) {
            wrapper.eq(PurchaseDetailEntity::getWareId, wareId);
        }

        String key = (String) params.get("key");
        if (StrUtil.isNotBlank(key)) {
            wrapper.and(w -> {
                w.eq(PurchaseDetailEntity::getSkuId, key)
                        .or().eq(PurchaseDetailEntity::getPurchaseId, key);
            });
        }

        IPage<PurchaseDetailEntity> page = this.page(new Query<PurchaseDetailEntity>().getPage(params), wrapper);
        return new PageUtils(page);
    }
}
