package com.sun.mall.common.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author sun
 */
@Data
public class SpuBoundsDTO {

    private Long spuId;

    private BigDecimal buyBounds;

    private BigDecimal growBounds;

}
