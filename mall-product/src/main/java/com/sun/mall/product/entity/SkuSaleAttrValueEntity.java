package com.sun.mall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * sku销售属性&值
 *
 * @author Jianda Sun
 * @email jianda_sun@qq.com
 * @date 2022-10-26 17:57:00
 */
@Data
@TableName("pms_sku_sale_attr_value")
public class SkuSaleAttrValueEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;

	/**
	 * sku_id
	 */
	private Long skuId;

	/**
	 * attr_id
	 */
	private Long attrId;

	/**
	 * 销售属性名
	 */
	private String attrName;

	/**
	 * 销售属性值
	 */
	private String attrValue;

	/**
	 * 顺序
	 */
	private Integer attrSort;

}
