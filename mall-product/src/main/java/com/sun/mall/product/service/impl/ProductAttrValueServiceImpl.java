package com.sun.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.mall.common.utils.PageUtils;
import com.sun.mall.common.utils.Query;
import com.sun.mall.product.dao.ProductAttrValueDao;
import com.sun.mall.product.entity.ProductAttrValueEntity;
import com.sun.mall.product.service.ProductAttrValueService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service("productAttrValueService")
public class ProductAttrValueServiceImpl extends ServiceImpl<ProductAttrValueDao, ProductAttrValueEntity> implements ProductAttrValueService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProductAttrValueEntity> page = this.page(
                new Query<ProductAttrValueEntity>().getPage(params),
                new QueryWrapper<ProductAttrValueEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<ProductAttrValueEntity> getBaseListForSpu(Long spuId) {
        return lambdaQuery().eq(ProductAttrValueEntity::getSpuId, spuId).list();
    }

    @Transactional
    @Override
    public void updateSpuAttr(Long spuId, List<ProductAttrValueEntity> productAttrValueList) {
        // 更新规格参数时，有的数据会新增、有的数据会修改、有的数据会被删除；可以直接删除所有的属性，然后直接新增即可。
        this.remove(lambdaQuery().eq(ProductAttrValueEntity::getSpuId, spuId));
        for (ProductAttrValueEntity productAttrValueEntity : productAttrValueList) {
            productAttrValueEntity.setSpuId(spuId);
        }
        this.saveBatch(productAttrValueList);
    }

}
