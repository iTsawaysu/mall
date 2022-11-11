package com.sun.mall.product.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.mall.common.utils.PageUtils;
import com.sun.mall.common.utils.Query;
import com.sun.mall.product.dao.BrandDao;
import com.sun.mall.product.entity.BrandEntity;
import com.sun.mall.product.entity.CategoryBrandRelationEntity;
import com.sun.mall.product.service.BrandService;
import com.sun.mall.product.service.CategoryBrandRelationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;


/**
 * @author sun
 */
@Service("brandService")
@SuppressWarnings("ALL")
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {

    @Resource
    private CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key = (String) params.get("key");
        LambdaQueryWrapper<BrandEntity> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(key)) {
            wrapper.like(BrandEntity::getBrandId, key).or().like(BrandEntity::getName, key);
        }
        IPage<BrandEntity> page = this.page(new Query<BrandEntity>().getPage(params), wrapper);
        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void updateBrandAndRelation(BrandEntity brand) {
        this.updateById(brand);
        CategoryBrandRelationEntity relation = new CategoryBrandRelationEntity();
        relation.setBrandId(brand.getBrandId());
        relation.setBrandName(brand.getName());
        if (StrUtil.isNotBlank(brand.getName())) {
            categoryBrandRelationService.update(
                    relation,
                    new UpdateWrapper<CategoryBrandRelationEntity>().eq("brand_id", brand.getBrandId())
            );
        }
    }

}
