package com.sun.mall.ware.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author sun
 */
@Data
public class PurchaseDoneVo {

    /**
     * 采购单 ID
     */
    @NotNull
    private Long id;

    /**
     * 采购项
     */
    private List<PurchaseItemVo> items;

}
