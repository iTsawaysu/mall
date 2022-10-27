package com.sun.mall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sun.mall.common.utils.PageUtils;
import com.sun.mall.coupon.entity.SeckillSkuRelationEntity;

import java.util.Map;

/**
 * 秒杀活动商品关联
 *
 * @author Jianda Sun
 * @email jianda_sun@qq.com
 * @date 2022-10-26 21:09:46
 */
public interface SeckillSkuRelationService extends IService<SeckillSkuRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}
