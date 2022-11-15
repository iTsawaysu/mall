package com.sun.mall.vo;

import lombok.Data;

/**
 * @author sun
 */

@Data
public class AttrResponseVo extends AttrVo {
    /**
     * 所属分类
     */
    private String catelogName;

    /**
     * 所属分组
     */
    private String groupName;

    /**
     * 属性的完整路径
     */
    private Long[] catelogPath;
}
