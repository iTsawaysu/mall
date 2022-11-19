package com.sun.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sun.mall.common.utils.PageUtils;
import com.sun.mall.product.entity.ProductAttrValueEntity;

import java.util.List;
import java.util.Map;

/**
 * spu属性值
 *
 * @author Jianda Sun
 * @email jianda_sun@qq.com
 * @date 2022-10-26 17:57:00
 */
public interface ProductAttrValueService extends IService<ProductAttrValueEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 获取 SPU 规格
     */
    List<ProductAttrValueEntity> getBaseListForSpu(Long spuId);

    /**
     * 修改商品规格
     */
    void updateSpuAttr(Long spuId, List<ProductAttrValueEntity> productAttrValueList);
}
