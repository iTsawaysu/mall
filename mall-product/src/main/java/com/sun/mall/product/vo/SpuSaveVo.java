package com.sun.mall.product.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author sun
 */
@Data
public class SpuSaveVo {

    private String spuName;

    private String spuDescription;

    private Long catalogId;

    private Long brandId;

    private BigDecimal weight;

    private Integer publishStatus;

    /**
     * 描述信息
     */
    private List<String> decript;

    /**
     * 图片集
     */
    private List<String> images;

    /**
     * 规格参数
     */
    private List<BaseAttrs> baseAttrs;

    /**
     * 积分信息
     */
    private Bounds bounds;

    /**
     * Sku 信息
     */
    private List<Skus> skus;

}
