package com.sun.mall.coupon.controller;

import com.sun.mall.common.utils.PageUtils;
import com.sun.mall.common.utils.R;
import com.sun.mall.coupon.entity.HomeSubjectEntity;
import com.sun.mall.coupon.service.HomeSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】
 *
 * @author Jianda Sun
 * @email jianda_sun@qq.com
 * @date 2022-10-26 21:09:46
 */
@RestController
@RequestMapping("/coupon/homeSubject")
public class HomeSubjectController {
    @Autowired
    private HomeSubjectService homeSubjectService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = homeSubjectService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        HomeSubjectEntity homeSubject = homeSubjectService.getById(id);
        return R.ok().put("homeSubject", homeSubject);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody HomeSubjectEntity homeSubject) {
        homeSubjectService.save(homeSubject);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody HomeSubjectEntity homeSubject) {
        homeSubjectService.updateById(homeSubject);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        homeSubjectService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
