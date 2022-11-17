package com.sun.mall.product.vo;

import com.sun.mall.common.dto.MemberPrice;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author sun
 */
@Data
public class Skus {
    private String skuName;

    private BigDecimal price;

    private String skuTitle;

    private String skuSubtitle;

    private Integer fullCount;

    private BigDecimal discount;

    private Integer countStatus;

    private BigDecimal fullPrice;

    private BigDecimal reducePrice;

    private Integer priceStatus;

    private List<String> descar;

    /**
     * 图片信息
     */
    private List<Images> images;

    /**
     * 销售属性
     */
    private List<Attr> attr;

    /**
     * 会员价格
     */
    private List<MemberPrice> memberPrice;

}
