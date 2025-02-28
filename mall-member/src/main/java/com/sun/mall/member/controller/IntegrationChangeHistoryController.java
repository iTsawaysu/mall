package com.sun.mall.member.controller;

import com.sun.mall.common.utils.PageUtils;
import com.sun.mall.common.utils.R;
import com.sun.mall.member.entity.IntegrationChangeHistoryEntity;
import com.sun.mall.member.service.IntegrationChangeHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 积分变化历史记录
 *
 * @author Jianda Sun
 * @email jianda_sun@qq.com
 * @date 2022-10-26 21:10:52
 */
@RestController
@RequestMapping("/member/integrationChangeHistory")
public class IntegrationChangeHistoryController {
    @Autowired
    private IntegrationChangeHistoryService integrationChangeHistoryService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = integrationChangeHistoryService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        IntegrationChangeHistoryEntity integrationChangeHistory = integrationChangeHistoryService.getById(id);
        return R.ok().put("integrationChangeHistory", integrationChangeHistory);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody IntegrationChangeHistoryEntity integrationChangeHistory) {
        integrationChangeHistoryService.save(integrationChangeHistory);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody IntegrationChangeHistoryEntity integrationChangeHistory) {
        integrationChangeHistoryService.updateById(integrationChangeHistory);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        integrationChangeHistoryService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
