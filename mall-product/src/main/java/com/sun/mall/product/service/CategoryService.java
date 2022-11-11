package com.sun.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sun.mall.common.utils.PageUtils;
import com.sun.mall.product.entity.CategoryEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品三级分类
 *
 * @author Jianda Sun
 * @email jianda_sun@qq.com
 * @date 2022-10-26 17:57:00
 */
@SuppressWarnings("ALL")
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查询所有分类及其子分类，以树形结构展示
     */
    List<CategoryEntity> displayWithTreeStructure();

    /**
     * 根据 ID 移除分类
     */
    void removeCategoriesByIds(List<Long> catIds);

    /**
     * 根据 三级分类ID 获取完整路径[1st, 2nd, 3rd]
     */
    Long[] getCompletePathByCategoryId(Long catelogId);

    /**
     * 更新分类表，并且更新品牌分类关联表。
     */
    void updateCategoryAndRelation(CategoryEntity category);
}

