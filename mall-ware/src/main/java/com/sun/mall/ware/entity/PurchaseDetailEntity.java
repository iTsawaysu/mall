package com.sun.mall.ware.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 *
 * @author Jianda Sun
 * @email jianda_sun@qq.com
 * @date 2022-10-26 21:06:58
 */
@Data
@TableName("wms_purchase_detail")
public class PurchaseDetailEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键 ID
	 */
	@TableId
	private Long id;

	/**
	 * 采购单id
	 */
	private Long purchaseId;

	/**
	 * 采购商品id
	 */
	private Long skuId;

	/**
	 * 采购数量
	 */
	private Integer skuNum;

	/**
	 * 采购金额
	 */
	private BigDecimal skuPrice;

	/**
	 * 仓库id
	 */
	private Long wareId;

	/**
	 * 状态[0新建，1已分配，2正在采购，3已完成，4采购失败]
	 */
	private Integer status;

}
