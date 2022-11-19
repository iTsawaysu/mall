package com.sun.mall.product.controller;

import com.sun.mall.common.utils.PageUtils;
import com.sun.mall.common.utils.R;
import com.sun.mall.product.entity.AttrAttrgroupRelationEntity;
import com.sun.mall.product.service.AttrAttrgroupRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 属性&属性分组关联
 *
 * @author Jianda Sun
 * @email jianda_sun@qq.com
 * @date 2022-10-26 20:56:18
 */
@RestController
@SuppressWarnings("ALL")
@RequestMapping("/product/attrAttrGroupRelation")
public class AttrAttrgroupRelationController {
    @Autowired
    private AttrAttrgroupRelationService attrAttrgroupRelationService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = attrAttrgroupRelationService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        AttrAttrgroupRelationEntity attrAttrgroupRelation = attrAttrgroupRelationService.getById(id);
        return R.ok().put("attrAttrgroupRelation", attrAttrgroupRelation);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody AttrAttrgroupRelationEntity attrAttrgroupRelation) {
        attrAttrgroupRelationService.save(attrAttrgroupRelation);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody AttrAttrgroupRelationEntity attrAttrgroupRelation) {
        attrAttrgroupRelationService.updateById(attrAttrgroupRelation);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        attrAttrgroupRelationService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
