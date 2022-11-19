package com.sun.mall.member.controller;

import com.sun.mall.common.utils.PageUtils;
import com.sun.mall.common.utils.R;
import com.sun.mall.member.entity.MemberStatisticsInfoEntity;
import com.sun.mall.member.service.MemberStatisticsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 会员统计信息
 *
 * @author Jianda Sun
 * @email jianda_sun@qq.com
 * @date 2022-10-26 21:10:52
 */
@RestController
@RequestMapping("/member/memberStatisticsInfo")
public class MemberStatisticsInfoController {
    @Autowired
    private MemberStatisticsInfoService memberStatisticsInfoService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = memberStatisticsInfoService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        MemberStatisticsInfoEntity memberStatisticsInfo = memberStatisticsInfoService.getById(id);
        return R.ok().put("memberStatisticsInfo", memberStatisticsInfo);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody MemberStatisticsInfoEntity memberStatisticsInfo) {
        memberStatisticsInfoService.save(memberStatisticsInfo);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody MemberStatisticsInfoEntity memberStatisticsInfo) {
        memberStatisticsInfoService.updateById(memberStatisticsInfo);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        memberStatisticsInfoService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
