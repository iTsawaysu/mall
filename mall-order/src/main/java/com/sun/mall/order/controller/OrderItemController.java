package com.sun.mall.order.controller;

import com.sun.mall.common.utils.PageUtils;
import com.sun.mall.common.utils.R;
import com.sun.mall.order.entity.OrderItemEntity;
import com.sun.mall.order.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 订单项信息
 *
 * @author Jianda Sun
 * @email jianda_sun@qq.com
 * @date 2022-10-26 21:14:06
 */
@RestController
@RequestMapping("/order/orderItem")
public class OrderItemController {
    @Autowired
    private OrderItemService orderItemService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = orderItemService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        OrderItemEntity orderItem = orderItemService.getById(id);
        return R.ok().put("orderItem", orderItem);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody OrderItemEntity orderItem) {
        orderItemService.save(orderItem);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody OrderItemEntity orderItem) {
        orderItemService.updateById(orderItem);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        orderItemService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
