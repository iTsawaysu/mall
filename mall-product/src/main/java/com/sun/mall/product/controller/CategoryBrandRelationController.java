package com.sun.mall.product.controller;

import com.sun.mall.common.utils.R;
import com.sun.mall.product.entity.CategoryBrandRelationEntity;
import com.sun.mall.product.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


/**
 * 品牌分类关联
 *
 * @author Jianda Sun
 * @email jianda_sun@qq.com
 * @date 2022-10-26 20:56:18
 */
@RestController
@SuppressWarnings("ALL")
@RequestMapping("/product/categoryBrandRelation")
public class CategoryBrandRelationController {
    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    /**
     * 根据 BrandId 查询 分类
     */
    @GetMapping("/category/list")
    public R list(@RequestParam("brandId") Long brandId) {
        List<CategoryBrandRelationEntity> categoryBrandRelationEntityList =
                categoryBrandRelationService.getCategoriesByBrandId(brandId);
        return R.ok().put("data", categoryBrandRelationEntityList);
    }


    /**
     * 新增 品牌&分类 关系
     */
    @RequestMapping("/save")
    public R save(@RequestBody CategoryBrandRelationEntity categoryBrandRelation) {
        categoryBrandRelationService.addBrandCategoryRelation(categoryBrandRelation);
        return R.ok();
    }


    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        categoryBrandRelationService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
