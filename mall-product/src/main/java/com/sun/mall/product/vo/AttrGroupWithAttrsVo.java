package com.sun.mall.product.vo;

import com.sun.mall.product.entity.AttrEntity;
import lombok.Data;

import java.util.List;

/**
 * @author sun
 */
@Data
public class AttrGroupWithAttrsVo {

    /**
     * 分组id
     */
    private Long attrGroupId;

    /**
     * 组名
     */
    private String attrGroupName;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 描述
     */
    private String descript;

    /**
     * 组图标
     */
    private String icon;

    /**
     * 所属分类id
     */
    private Long catelogId;

    /**
     * 分组所包含的属性
     */
    private List<AttrEntity> attrs;
}
