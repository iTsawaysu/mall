package com.sun.mall.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.mall.product.dao.CategoryBrandRelationDao;
import com.sun.mall.product.entity.BrandEntity;
import com.sun.mall.product.entity.CategoryBrandRelationEntity;
import com.sun.mall.product.entity.CategoryEntity;
import com.sun.mall.product.service.BrandService;
import com.sun.mall.product.service.CategoryBrandRelationService;
import com.sun.mall.product.service.CategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author sun
 */
@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity> implements CategoryBrandRelationService {

    @Resource
    private BrandService brandService;

    @Resource
    private CategoryService categoryService;

    @Override
    public List<CategoryBrandRelationEntity> getCategoriesByBrandId(Long brandId) {
        return lambdaQuery().eq(CategoryBrandRelationEntity::getBrandId, brandId).list();
    }

    @Override
    public void addBrandCategoryRelation(CategoryBrandRelationEntity categoryBrandRelation) {
        Long brandId = categoryBrandRelation.getBrandId();
        Long catelogId = categoryBrandRelation.getCatelogId();
        BrandEntity brand = brandService.getById(brandId);
        CategoryEntity category = categoryService.getById(catelogId);
        categoryBrandRelation.setBrandName(brand.getName());
        categoryBrandRelation.setCatelogName(category.getName());
        this.save(categoryBrandRelation);
    }

}
