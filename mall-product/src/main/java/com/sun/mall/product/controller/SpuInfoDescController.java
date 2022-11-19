package com.sun.mall.product.controller;

import com.sun.mall.common.utils.PageUtils;
import com.sun.mall.common.utils.R;
import com.sun.mall.product.entity.SpuInfoDescEntity;
import com.sun.mall.product.service.SpuInfoDescService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * spu信息介绍
 *
 * @author Jianda Sun
 * @email jianda_sun@qq.com
 * @date 2022-10-26 20:56:18
 */
@RestController
@SuppressWarnings("ALL")
@RequestMapping("/product/spuInfoDesc")
public class SpuInfoDescController {
    @Autowired
    private SpuInfoDescService spuInfoDescService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = spuInfoDescService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{spuId}")
    public R info(@PathVariable("spuId") Long spuId) {
        SpuInfoDescEntity spuInfoDesc = spuInfoDescService.getById(spuId);
        return R.ok().put("spuInfoDesc", spuInfoDesc);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody SpuInfoDescEntity spuInfoDesc) {
        spuInfoDescService.save(spuInfoDesc);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody SpuInfoDescEntity spuInfoDesc) {
        spuInfoDescService.updateById(spuInfoDesc);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] spuIds) {
        spuInfoDescService.removeByIds(Arrays.asList(spuIds));
        return R.ok();
    }

}
