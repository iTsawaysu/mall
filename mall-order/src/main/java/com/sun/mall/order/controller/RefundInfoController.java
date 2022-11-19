package com.sun.mall.order.controller;

import com.sun.mall.common.utils.PageUtils;
import com.sun.mall.common.utils.R;
import com.sun.mall.order.entity.RefundInfoEntity;
import com.sun.mall.order.service.RefundInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 退款信息
 *
 * @author Jianda Sun
 * @email jianda_sun@qq.com
 * @date 2022-10-26 21:14:06
 */
@RestController
@RequestMapping("/order/refundInfo")
public class RefundInfoController {
    @Autowired
    private RefundInfoService refundInfoService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = refundInfoService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        RefundInfoEntity refundInfo = refundInfoService.getById(id);
        return R.ok().put("refundInfo", refundInfo);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody RefundInfoEntity refundInfo) {
        refundInfoService.save(refundInfo);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody RefundInfoEntity refundInfo) {
        refundInfoService.updateById(refundInfo);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        refundInfoService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
