package com.sun.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sun.mall.common.utils.PageUtils;
import com.sun.mall.product.entity.BrandEntity;

import java.util.Map;

/**
 * 品牌
 *
 * @author Jianda Sun
 * @email jianda_sun@qq.com
 * @date 2022-10-26 17:57:00
 */
public interface BrandService extends IService<BrandEntity> {

    PageUtils queryPage(Map<String, Object> params);


    /**
     * 更新品牌表，并且更新品牌分类关联表。
     */
    void updateBrandAndRelation(BrandEntity brand);
}

