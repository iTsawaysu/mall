package com.sun.mall.order.controller;

import com.sun.mall.common.utils.PageUtils;
import com.sun.mall.common.utils.R;
import com.sun.mall.order.entity.OrderReturnApplyEntity;
import com.sun.mall.order.service.OrderReturnApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 订单退货申请
 *
 * @author Jianda Sun
 * @email jianda_sun@qq.com
 * @date 2022-10-26 21:14:06
 */
@RestController
@RequestMapping("/order/orderReturnApply")
public class OrderReturnApplyController {
    @Autowired
    private OrderReturnApplyService orderReturnApplyService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = orderReturnApplyService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        OrderReturnApplyEntity orderReturnApply = orderReturnApplyService.getById(id);
        return R.ok().put("orderReturnApply", orderReturnApply);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody OrderReturnApplyEntity orderReturnApply) {
        orderReturnApplyService.save(orderReturnApply);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody OrderReturnApplyEntity orderReturnApply) {
        orderReturnApplyService.updateById(orderReturnApply);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        orderReturnApplyService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
