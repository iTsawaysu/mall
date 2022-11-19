package com.sun.mall.ware.controller;

import com.sun.mall.common.utils.PageUtils;
import com.sun.mall.common.utils.R;
import com.sun.mall.ware.entity.PurchaseEntity;
import com.sun.mall.ware.service.PurchaseService;
import com.sun.mall.ware.vo.MergeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 采购信息
 *
 * @author Jianda Sun
 * @email jianda_sun@qq.com
 * @date 2022-10-26 21:06:58
 */
@RestController
@RequestMapping("/ware/purchase")
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;

    /**
     * 根据 params 条件查询展示 新建、已分配 状态的采购单
     */
    @GetMapping("/unreceived/list")
    public R getUnreceivedPurchase(@RequestParam Map<String, Object> params) {
        PageUtils page = purchaseService.queryUnreceivedPurchasePageByParams(params);
        return R.ok().put("page", page);
    }

    /**
     * 领取采购单
     */
    @PostMapping("/received")
    public R received(@RequestBody List<Long> purchaseOrderIdList) {
        purchaseService.receivedThePurchaseOrder(purchaseOrderIdList);
        return R.ok();
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = purchaseService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        PurchaseEntity purchase = purchaseService.getById(id);
        return R.ok().put("purchase", purchase);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody PurchaseEntity purchase) {
        purchase.setCreateTime(new Date());
        purchase.setUpdateTime(new Date());
        purchaseService.save(purchase);
        return R.ok();
    }

    /**
     * 合并采购需求到采购单（如果没有采购单则新建一个采购单再合并到里面）
     */
    @PostMapping("/merge")
    public R mergePurchaseDetails(@RequestBody MergeVo mergeVo) {
        purchaseService.mergePurchaseDetailsToPurchaseOrder(mergeVo);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody PurchaseEntity purchase) {
        purchaseService.updateById(purchase);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        purchaseService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
