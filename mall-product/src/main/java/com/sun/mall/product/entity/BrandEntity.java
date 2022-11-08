package com.sun.mall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sun.mall.common.validation.AddGroup;
import com.sun.mall.common.validation.OptionalValue;
import com.sun.mall.common.validation.UpdateGroup;
import com.sun.mall.common.validation.UpdateStatusGroup;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * 品牌
 *
 * @author Jianda Sun
 * @email jianda_sun@qq.com
 * @date 2022-10-26 17:57:00
 */
@Data
@TableName("pms_brand")
public class BrandEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 品牌id
	 */
	@Null(message = "新增时不能指定 ID", groups = {AddGroup.class})
	@NotNull(message = "修改时必须指定 ID", groups = {UpdateGroup.class})
	@TableId
	private Long brandId;

	/**
	 * 品牌名
	 */
	@NotBlank(message = "name 不能为空~", groups = {AddGroup.class, UpdateGroup.class})
	private String name;

	/**
	 * 品牌logo地址
	 */
	@URL(message = "logo 必须是一个合法的 URL 地址~", groups = {AddGroup.class, UpdateGroup.class})
	@NotBlank(message = "logo 不能为 null~", groups = {AddGroup.class})
	private String logo;

	/**
	 * 介绍
	 */
	private String descript;

	/**
	 * 显示状态[0-不显示；1-显示]
	 * @OptionalValue 可选值为 ?
	 */
	@NotNull(groups = {AddGroup.class, UpdateStatusGroup.class})
	@OptionalValue(values = {0, 1}, groups = {AddGroup.class, UpdateStatusGroup.class})
	private Integer showStatus;

	/**
	 * 检索首字母
	 */
	@NotBlank(message = "firstLetter 不能为 null~", groups = {AddGroup.class})
	@Pattern(regexp = "^[a-zA-Z]$", message = "firstLetter 必须是一个字母~", groups = {AddGroup.class, UpdateGroup.class})
	private String firstLetter;

	/**
	 * 排序
	 */
	@NotNull(message = "sort 不能为 null~", groups = {AddGroup.class})
	@Min(value = 0, message = "sort 的值必须大于 0~", groups = {AddGroup.class, UpdateGroup.class})
	private Integer sort;

}
