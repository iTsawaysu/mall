package com.sun.mall.product.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 属性分组
 *
 * @author Jianda Sun
 * @email jianda_sun@qq.com
 * @date 2022-10-26 17:57:00
 */
@Data
@TableName("pms_attr_group")
public class AttrGroupEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 分组id
	 */
	@TableId
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
	 * 三级分类完整路径 [一级分类ID, 二级分类ID, 三级分类ID]
	 */
	@TableField(exist = false)
	private Long[] catelogPath;
}
