package com.sun.mall.product.controller;

import com.sun.mall.common.utils.PageUtils;
import com.sun.mall.common.utils.R;
import com.sun.mall.product.entity.AttrEntity;
import com.sun.mall.product.entity.AttrGroupEntity;
import com.sun.mall.product.service.AttrAttrgroupRelationService;
import com.sun.mall.product.service.AttrGroupService;
import com.sun.mall.product.service.AttrService;
import com.sun.mall.product.service.CategoryService;
import com.sun.mall.product.vo.AttrGroupRelationVo;
import com.sun.mall.product.vo.AttrGroupWithAttrsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 属性分组
 *
 * @author Jianda Sun
 * @email jianda_sun@qq.com
 * @date 2022-10-26 20:56:18
 */
@RestController
@SuppressWarnings("ALL")
@RequestMapping("/product/attrGroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;

    @Resource
    private CategoryService categoryService;

    @Resource
    private AttrService attrService;

    @Resource
    private AttrAttrgroupRelationService attrAttrgroupRelationService;

    /**
     * 根据 catelogId 获取该分类下的分组及其属性
     */
    @GetMapping("/{catelogId}/withAttr")
    public R getAttrGroupWithAttrsByCategoryId(@PathVariable("catelogId") Long catelogId) {
        List<AttrGroupWithAttrsVo> attrGroupWithAttrsVoList = attrGroupService.getAttrGroupWithAttrsByCategoryId(catelogId);
        return R.ok().put("data", attrGroupWithAttrsVoList);
    }

    /**
     * 根据 groupId 查询 当前分组关联的所有属性
     */
    @GetMapping("/{groupId}/attr/relation")
    public R getAssociatedAttrByGroupId(@PathVariable("groupId") Long groupId) {
        List<AttrEntity> attrEntityList = attrService.getAssociatedAttrByGroupId(groupId);
        return R.ok().put("data", attrEntityList);
    }

    /**
     * 根据 groupId 查询 当前分组未关联的所有属性
     */
    @GetMapping("/{groupId}/noAttr/relation")
    public R getNotAssociatedAttrByGroupId(@PathVariable("groupId") Long groupId,
                                           @RequestParam Map<String, Object> params) {
        PageUtils page = attrService.getNotAssociatedAttrByGroupId(groupId, params);
        return R.ok().put("page", page);
    }

    /**
     * 根据 分类ID 查询 属性分组
     */
    @GetMapping("/list/{catelogId}")
    public R listByCatelogId(@RequestParam Map<String, Object> params, @PathVariable("catelogId") Long catelogId) {
        PageUtils page = attrGroupService.queryAttrGroupsByCategoryId(params, catelogId);
        return R.ok().put("page", page);
    }

    /**
     * 根据 属性分组ID 获取属性分组（根据 分类ID 获取其完整路径）
     */
    @GetMapping("/info/{attrGroupId}")
    public R info(@PathVariable("attrGroupId") Long attrGroupId) {
        AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);
        Long catId = attrGroup.getCatelogId();
        Long[] catelogPath = categoryService.getCompletePathByCategoryId(catId);
        attrGroup.setCatelogPath(catelogPath);
        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * 添加属性和分组的关联关系
     */
    @PostMapping("/attr/relation")
    public R addRelation(@RequestBody List<AttrGroupRelationVo> attrGroupRelationVoList) {
        attrAttrgroupRelationService.saveBatchAssociatedRelation(attrGroupRelationVoList);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] attrGroupIds) {
        attrGroupService.removeByIds(Arrays.asList(attrGroupIds));
        return R.ok();
    }

    /**
     * 删除 属性分组 和 属性 间的关联
     */
    @PostMapping("/attr/relation/delete")
    public R deleteTheRelations(@RequestBody List<AttrGroupRelationVo> attrGroupRelationVoList) {
        attrService.deleteTheRelations(attrGroupRelationVoList);
        return R.ok();
    }

}
