package com.sun.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sun.mall.common.utils.PageUtils;
import com.sun.mall.product.entity.AttrGroupEntity;

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
}

