package com.sun.mall.member.controller;

import com.sun.mall.common.utils.PageUtils;
import com.sun.mall.common.utils.R;
import com.sun.mall.member.entity.GrowthChangeHistoryEntity;
import com.sun.mall.member.service.GrowthChangeHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 成长值变化历史记录
 *
 * @author Jianda Sun
 * @email jianda_sun@qq.com
 * @date 2022-10-26 21:10:52
 */
@RestController
@RequestMapping("/member/growthChangeHistory")
public class GrowthChangeHistoryController {
    @Autowired
    private GrowthChangeHistoryService growthChangeHistoryService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = growthChangeHistoryService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        GrowthChangeHistoryEntity growthChangeHistory = growthChangeHistoryService.getById(id);
        return R.ok().put("growthChangeHistory", growthChangeHistory);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody GrowthChangeHistoryEntity growthChangeHistory) {
        growthChangeHistoryService.save(growthChangeHistory);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody GrowthChangeHistoryEntity growthChangeHistory) {
        growthChangeHistoryService.updateById(growthChangeHistory);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        growthChangeHistoryService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
