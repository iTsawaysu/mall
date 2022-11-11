package com.sun.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sun.mall.product.entity.CategoryBrandRelationEntity;

import java.util.List;

/**
 * 品牌分类关联
 *
 * @author Jianda Sun
 * @email jianda_sun@qq.com
 * @date 2022-10-26 17:57:00
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelationEntity> {

    /**
     * 根据 BrandId 查询分类
     */
    List<CategoryBrandRelationEntity> getCategoriesByBrandId(Long brandId);

    /**
     * 新增 品牌&分类 关系
     */
    void addBrandCategoryRelation(CategoryBrandRelationEntity categoryBrandRelation);
}

