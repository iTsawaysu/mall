package com.sun.mall.member.controller;

import com.sun.mall.common.utils.PageUtils;
import com.sun.mall.common.utils.R;
import com.sun.mall.member.entity.MemberLoginLogEntity;
import com.sun.mall.member.service.MemberLoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 会员登录记录
 *
 * @author Jianda Sun
 * @email jianda_sun@qq.com
 * @date 2022-10-26 21:10:52
 */
@RestController
@RequestMapping("/member/memberLoginLog")
public class MemberLoginLogController {

    @Autowired
    private MemberLoginLogService memberLoginLogService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = memberLoginLogService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        MemberLoginLogEntity memberLoginLog = memberLoginLogService.getById(id);
        return R.ok().put("memberLoginLog", memberLoginLog);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody MemberLoginLogEntity memberLoginLog) {
        memberLoginLogService.save(memberLoginLog);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody MemberLoginLogEntity memberLoginLog) {
        memberLoginLogService.updateById(memberLoginLog);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        memberLoginLogService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
