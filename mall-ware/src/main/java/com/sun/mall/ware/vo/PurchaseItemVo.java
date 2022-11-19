package com.sun.mall.ware.vo;

import lombok.Data;

/**
 * @author sun
 */

@Data
public class PurchaseItemVo {
    private Long itemId;

    private Integer status;

    private String reason;
}
