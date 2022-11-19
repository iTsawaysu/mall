package com.sun.mall.ware.vo;

import lombok.Data;

import java.util.List;

/**
 * @author sun
 */

@Data
public class MergeVo {

    /**
     * 整单 ID
     */
    private Long purchaseId;

    /**
     * 合并项（采购需求）的 ID 集合
     */
    private List<Long> items;

}
