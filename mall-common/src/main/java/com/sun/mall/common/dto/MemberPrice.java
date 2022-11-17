package com.sun.mall.common.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author sun
 */
@Data
public class MemberPrice {

    private Long id;

    private String name;

    private BigDecimal price;

}
