package com.sun.mall.coupon.controller;

import com.sun.mall.common.utils.PageUtils;
import com.sun.mall.common.utils.R;
import com.sun.mall.coupon.entity.SeckillPromotionEntity;
import com.sun.mall.coupon.service.SeckillPromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 秒杀活动
 *
 * @author Jianda Sun
 * @email jianda_sun@qq.com
 * @date 2022-10-26 21:09:46
 */
@RestController
@RequestMapping("/coupon/seckillPromotion")
public class SeckillPromotionController {
    @Autowired
    private SeckillPromotionService seckillPromotionService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = seckillPromotionService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        SeckillPromotionEntity seckillPromotion = seckillPromotionService.getById(id);
        return R.ok().put("seckillPromotion", seckillPromotion);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody SeckillPromotionEntity seckillPromotion) {
        seckillPromotionService.save(seckillPromotion);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody SeckillPromotionEntity seckillPromotion) {
        seckillPromotionService.updateById(seckillPromotion);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        seckillPromotionService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
