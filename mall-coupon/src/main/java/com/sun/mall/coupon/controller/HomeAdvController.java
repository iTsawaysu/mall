package com.sun.mall.coupon.controller;

import com.sun.mall.common.utils.PageUtils;
import com.sun.mall.common.utils.R;
import com.sun.mall.coupon.entity.HomeAdvEntity;
import com.sun.mall.coupon.service.HomeAdvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 首页轮播广告
 *
 * @author Jianda Sun
 * @email jianda_sun@qq.com
 * @date 2022-10-26 21:09:46
 */
@RestController
@RequestMapping("/coupon/homeAdv")
public class HomeAdvController {
    @Autowired
    private HomeAdvService homeAdvService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = homeAdvService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        HomeAdvEntity homeAdv = homeAdvService.getById(id);
        return R.ok().put("homeAdv", homeAdv);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody HomeAdvEntity homeAdv) {
        homeAdvService.save(homeAdv);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody HomeAdvEntity homeAdv) {
        homeAdvService.updateById(homeAdv);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        homeAdvService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
