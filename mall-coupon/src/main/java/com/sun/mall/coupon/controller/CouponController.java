package com.sun.mall.coupon.controller;

import com.sun.mall.common.utils.PageUtils;
import com.sun.mall.common.utils.R;
import com.sun.mall.coupon.entity.CouponEntity;
import com.sun.mall.coupon.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 优惠券信息
 *
 * @author Jianda Sun
 * @email jianda_sun@qq.com
 * @date 2022-10-26 21:09:46
 */
@RefreshScope
@RestController
@RequestMapping("/coupon/coupon")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @GetMapping("/test")
    public R test() {
        return R.ok().put("data", "openFeign OK~~~");
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = couponService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        CouponEntity coupon = couponService.getById(id);
        return R.ok().put("coupon", coupon);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody CouponEntity coupon) {
        couponService.save(coupon);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody CouponEntity coupon) {
        couponService.updateById(coupon);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        couponService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
