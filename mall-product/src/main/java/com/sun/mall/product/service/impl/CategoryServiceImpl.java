package com.sun.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.mall.common.utils.PageUtils;
import com.sun.mall.common.utils.Query;
import com.sun.mall.product.dao.CategoryDao;
import com.sun.mall.product.entity.CategoryEntity;
import com.sun.mall.product.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author sun
 */
@Service("categoryService")
@SuppressWarnings("ALL")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );
        return new PageUtils(page);
    }

    /**
     * 查询所有分类及其子分类，以树形结构展示
     */
    @Override
    public List<CategoryEntity> displayWithTreeStructure() {
        // 1. 查出所有分类
        List<CategoryEntity> categoryEntityList = query().list();

        // 2. 组装为父子的树形结构
        List<CategoryEntity> categoriesWithTreeStructure = categoryEntityList.stream()
                // 2.1 找到所有的一级分类（父分类ID 为 0）
                .filter(categoryEntity -> categoryEntity.getParentCid().longValue() == 0)
                .map(levelOneCategory -> {
                    // 2.2 为所有的一级分类设置 children
                    levelOneCategory.setChildren(setChildrenCategory(levelOneCategory, categoryEntityList));
                    return levelOneCategory;
                })
                // 2.3 排序
                .sorted((o1, o2) -> {
                    return (o1.getSort() == null ? 0 : o1.getSort()) - (o2.getSort() == null ? 0 : o2.getSort());
                })
                .collect(Collectors.toList());
        return categoriesWithTreeStructure;
    }

    /**
     * 递归查找所有子分类
     * @param rootCategory 当前分类
     * @param allCategories 所有分类
     * @return 子分类
     */
    private List<CategoryEntity> setChildrenCategory(CategoryEntity rootCategory, List<CategoryEntity> allCategories) {
        List<CategoryEntity> subcategoryList = allCategories.stream()
                // 找到子菜单
                .filter(categoryEntity -> categoryEntity.getParentCid().longValue() == rootCategory.getCatId().longValue())
                .map(childrenCategory -> {
                    childrenCategory.setChildren(setChildrenCategory(childrenCategory, allCategories));
                    return childrenCategory;
                })
                .sorted((o1, o2) -> {
                    return (o1.getSort() == null ? 0 : o1.getSort()) - (o2.getSort() == null ? 0 : o2.getSort());
                })
                .collect(Collectors.toList());
        return subcategoryList;
    }



    @Override
    public void removeCategoriesByIds(List<Long> catIds) {
        // TODO 判断当前删除菜单是否被其他地方引用
        this.removeByIds(catIds);
    }

}
