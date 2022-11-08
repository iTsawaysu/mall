package com.sun.mall.product.controller;

import com.sun.mall.common.utils.R;
import com.sun.mall.product.entity.CategoryEntity;
import com.sun.mall.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * 商品三级分类
 *
 * @author Jianda Sun
 * @email jianda_sun@qq.com
 * @date 2022-10-26 20:56:18
 */
@RestController
@RequestMapping("/product/category")
@SuppressWarnings("ALL")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 查询所有分类及其子分类，以树形结构展示
     */
    @RequestMapping("/list/tree")
    public R list() {
        List<CategoryEntity> categoryEntityList = categoryService.displayWithTreeStructure();
        return R.ok().put("data", categoryEntityList);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{catId}")
    public R info(@PathVariable("catId") Long catId) {
        CategoryEntity category = categoryService.getById(catId);
        return R.ok().put("category", category);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CategoryEntity category) {
        categoryService.save(category);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CategoryEntity category) {
        categoryService.updateById(category);
        return R.ok();
    }

    /**
     * 批量修改
     */
    @RequestMapping("/update/sort")
    public R updateBatch(@RequestBody CategoryEntity[] categoryEntities) {
         categoryService.updateBatchById(Arrays.asList(categoryEntities));
        return R.ok();
    }

    /**
     * 删除
     *
     * @ReqeustBody 获取请求体中的内容，只有 POST 请求与请求体
     * Spring MVC 自动将请求体中的数据（JSON）转换为对应的对象 Long[]
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] catIds) {
        categoryService.removeCategoriesByIds(Arrays.asList(catIds));
        return R.ok();
    }

}
