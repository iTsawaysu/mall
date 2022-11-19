package com.sun.mall.ware.controller;

import com.sun.mall.common.utils.PageUtils;
import com.sun.mall.common.utils.R;
import com.sun.mall.ware.entity.PurchaseDetailEntity;
import com.sun.mall.ware.service.PurchaseDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * @author Jianda Sun
 * @email jianda_sun@qq.com
 * @date 2022-10-26 21:06:58
 */
@RestController
@RequestMapping("/ware/purchaseDetail")
public class PurchaseDetailController {
    @Autowired
    private PurchaseDetailService purchaseDetailService;

    /**
     * 根据 params 条件分页查询展示 采购需求
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = purchaseDetailService.queryPurchaseDetailPageByParams(params);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        PurchaseDetailEntity purchaseDetail = purchaseDetailService.getById(id);
        return R.ok().put("purchaseDetail", purchaseDetail);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody PurchaseDetailEntity purchaseDetail) {
        purchaseDetailService.save(purchaseDetail);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody PurchaseDetailEntity purchaseDetail) {
        purchaseDetailService.updateById(purchaseDetail);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        purchaseDetailService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
