package com.sun.mall.member.controller;

import com.sun.mall.common.utils.PageUtils;
import com.sun.mall.common.utils.R;
import com.sun.mall.member.entity.MemberCollectSpuEntity;
import com.sun.mall.member.service.MemberCollectSpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 会员收藏的商品
 *
 * @author Jianda Sun
 * @email jianda_sun@qq.com
 * @date 2022-10-26 21:10:52
 */
@RestController
@RequestMapping("/member/memberCollectSpu")
public class MemberCollectSpuController {
    @Autowired
    private MemberCollectSpuService memberCollectSpuService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = memberCollectSpuService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        MemberCollectSpuEntity memberCollectSpu = memberCollectSpuService.getById(id);
        return R.ok().put("memberCollectSpu", memberCollectSpu);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody MemberCollectSpuEntity memberCollectSpu) {
        memberCollectSpuService.save(memberCollectSpu);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody MemberCollectSpuEntity memberCollectSpu) {
        memberCollectSpuService.updateById(memberCollectSpu);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        memberCollectSpuService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
