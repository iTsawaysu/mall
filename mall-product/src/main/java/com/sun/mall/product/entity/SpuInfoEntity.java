package com.sun.mall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * spu信息
 *
 * @author Jianda Sun
 * @email jianda_sun@qq.com
 * @date 2022-10-26 17:57:00
 */
@Data
@TableName("pms_spu_info")
public class SpuInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 商品id
	 */
	@TableId
	private Long id;

	/**
	 * 商品名称
	 */
	private String spuName;

	/**
	 * 商品描述
	 */
	private String spuDescription;

	/**
	 * 所属分类id
	 */
	private Long catalogId;

	/**
	 * 品牌id
	 */
	private Long brandId;

	private BigDecimal weight;

	/**
	 * 上架状态[0 - 下架，1 - 上架]
	 */
	private Integer publishStatus;

	//@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
	private Date createTime;

	//@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
	private Date updateTime;

}
