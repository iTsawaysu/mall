package com.sun.mall.order.controller;

import com.sun.mall.common.utils.PageUtils;
import com.sun.mall.common.utils.R;
import com.sun.mall.order.entity.OrderOperateHistoryEntity;
import com.sun.mall.order.service.OrderOperateHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 订单操作历史记录
 *
 * @author Jianda Sun
 * @email jianda_sun@qq.com
 * @date 2022-10-26 21:14:06
 */
@RestController
@RequestMapping("/order/orderOperateHistory")
public class OrderOperateHistoryController {
    @Autowired
    private OrderOperateHistoryService orderOperateHistoryService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = orderOperateHistoryService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        OrderOperateHistoryEntity orderOperateHistory = orderOperateHistoryService.getById(id);
        return R.ok().put("orderOperateHistory", orderOperateHistory);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody OrderOperateHistoryEntity orderOperateHistory) {
        orderOperateHistoryService.save(orderOperateHistory);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody OrderOperateHistoryEntity orderOperateHistory) {
        orderOperateHistoryService.updateById(orderOperateHistory);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        orderOperateHistoryService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
