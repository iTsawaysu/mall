package com.sun.mall.common.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author sun
 */
@Data
public class SkuReductionDTO {

    private Long skuId;

    private Integer fullCount;

    private BigDecimal discount;

    private Integer countStatus;

    private BigDecimal fullPrice;

    private BigDecimal reducePrice;

    private Integer priceStatus;

    private List<MemberPrice> memberPrice;
}
