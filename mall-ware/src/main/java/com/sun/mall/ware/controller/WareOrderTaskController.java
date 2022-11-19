package com.sun.mall.ware.controller;

import com.sun.mall.common.utils.PageUtils;
import com.sun.mall.common.utils.R;
import com.sun.mall.ware.entity.WareOrderTaskEntity;
import com.sun.mall.ware.service.WareOrderTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 库存工作单
 *
 * @author Jianda Sun
 * @email jianda_sun@qq.com
 * @date 2022-10-26 21:06:58
 */
@RestController
@RequestMapping("/ware/wareOrderTask")
public class WareOrderTaskController {
    @Autowired
    private WareOrderTaskService wareOrderTaskService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = wareOrderTaskService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        WareOrderTaskEntity wareOrderTask = wareOrderTaskService.getById(id);
        return R.ok().put("wareOrderTask", wareOrderTask);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody WareOrderTaskEntity wareOrderTask) {
        wareOrderTaskService.save(wareOrderTask);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody WareOrderTaskEntity wareOrderTask) {
        wareOrderTaskService.updateById(wareOrderTask);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        wareOrderTaskService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
