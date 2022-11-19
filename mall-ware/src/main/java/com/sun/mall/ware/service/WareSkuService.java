package com.sun.mall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sun.mall.common.utils.PageUtils;
import com.sun.mall.ware.entity.WareSkuEntity;

import java.util.Map;

/**
 * 商品库存
 *
 * @author Jianda Sun
 * @email jianda_sun@qq.com
 * @date 2022-10-26 21:06:58
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryWareSkuPageByParams(Map<String, Object> params);

    void addStock(Long skuId, Long wareId, Integer skuNum);
}

