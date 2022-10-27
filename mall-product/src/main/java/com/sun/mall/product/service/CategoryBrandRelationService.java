package com.sun.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sun.mall.common.utils.PageUtils;
import com.sun.mall.product.entity.CategoryBrandRelationEntity;

import java.util.Map;

/**
 * 品牌分类关联
 *
 * @author Jianda Sun
 * @email jianda_sun@qq.com
 * @date 2022-10-26 17:57:00
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

