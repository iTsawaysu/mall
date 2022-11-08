package com.sun.mall.product.controller;

import com.sun.mall.common.utils.PageUtils;
import com.sun.mall.common.utils.R;
import com.sun.mall.common.validation.AddGroup;
import com.sun.mall.common.validation.UpdateGroup;
import com.sun.mall.common.validation.UpdateStatusGroup;
import com.sun.mall.product.entity.BrandEntity;
import com.sun.mall.product.service.BrandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 品牌
 *
 * @author Jianda Sun
 * @email jianda_sun@qq.com
 * @date 2022-10-26 20:56:18
 */
@Slf4j
@RestController
@RequestMapping("/product/brand")
@SuppressWarnings("ALL")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    // @RequiresPermissions("product:brand:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = brandService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{brandId}")
    // @RequiresPermissions("product:brand:info")
    public R info(@PathVariable("brandId") Long brandId) {
        BrandEntity brand = brandService.getById(brandId);
        return R.ok().put("brand", brand);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    // @RequiresPermissions("product:brand:save")
    public R save(@Validated({AddGroup.class}) @RequestBody BrandEntity brand) {
        brandService.save(brand);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("product:brand:update")
    public R update(@Validated({UpdateGroup.class}) @RequestBody BrandEntity brand) {
        brandService.updateById(brand);
        return R.ok();
    }

    /**
     * 修改状态
     */
    @RequestMapping("/update/status")
    public R updateStatus(@Validated({UpdateStatusGroup.class}) @RequestBody BrandEntity brand) {
        brandService.updateById(brand);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("product:brand:delete")
    public R delete(@RequestBody Long[] brandIds) {
        brandService.removeByIds(Arrays.asList(brandIds));
        return R.ok();
    }
}
