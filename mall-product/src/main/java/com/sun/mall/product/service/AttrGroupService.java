package com.sun.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sun.mall.common.utils.PageUtils;
import com.sun.mall.product.entity.AttrGroupEntity;
import com.sun.mall.product.vo.AttrGroupWithAttrsVo;

import java.util.List;
import java.util.Map;

/**
 * 属性分组
 *
 * @author Jianda Sun
 * @email jianda_sun@qq.com
 * @date 2022-10-26 17:57:00
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    /**
     * 根据 分类ID 查询 属性分组
     */
    PageUtils queryAttrGroupsByCategoryId(Map<String, Object> params, Long catelogId);

    /**
     * 根据 catelogId 获取该分类下的分组及其属性
     */
    List<AttrGroupWithAttrsVo> getAttrGroupWithAttrsByCategoryId(Long catelogId);
}

