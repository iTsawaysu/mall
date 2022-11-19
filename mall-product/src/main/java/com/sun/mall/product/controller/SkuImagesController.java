package com.sun.mall.product.controller;

import com.sun.mall.common.utils.PageUtils;
import com.sun.mall.common.utils.R;
import com.sun.mall.product.entity.SkuImagesEntity;
import com.sun.mall.product.service.SkuImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * sku图片
 *
 * @author Jianda Sun
 * @email jianda_sun@qq.com
 * @date 2022-10-26 20:56:18
 */
@RestController
@SuppressWarnings("ALL")
@RequestMapping("/product/skuImages")
public class SkuImagesController {
    @Autowired
    private SkuImagesService skuImagesService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = skuImagesService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        SkuImagesEntity skuImages = skuImagesService.getById(id);
        return R.ok().put("skuImages", skuImages);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody SkuImagesEntity skuImages) {
        skuImagesService.save(skuImages);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody SkuImagesEntity skuImages) {
        skuImagesService.updateById(skuImages);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        skuImagesService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
