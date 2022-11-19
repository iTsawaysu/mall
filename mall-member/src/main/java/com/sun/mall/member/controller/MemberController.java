package com.sun.mall.member.controller;

import com.sun.mall.common.utils.PageUtils;
import com.sun.mall.common.utils.R;
import com.sun.mall.member.entity.MemberEntity;
import com.sun.mall.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 会员
 *
 * @author Jianda Sun
 * @email jianda_sun@qq.com
 * @date 2022-10-26 21:10:52
 */
@RestController
@RequestMapping("/member/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = memberService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        MemberEntity member = memberService.getById(id);
        return R.ok().put("member", member);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody MemberEntity member) {
        memberService.save(member);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody MemberEntity member) {
        memberService.updateById(member);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        memberService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
