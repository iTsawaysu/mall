package com.sun.mall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sun.mall.common.utils.PageUtils;
import com.sun.mall.ware.entity.PurchaseDetailEntity;

import java.util.Map;

/**
 *
 *
 * @author Jianda Sun
 * @email jianda_sun@qq.com
 * @date 2022-10-26 21:06:58
 */
public interface PurchaseDetailService extends IService<PurchaseDetailEntity> {

    PageUtils queryPurchaseDetailPageByParams(Map<String, Object> params);
}

