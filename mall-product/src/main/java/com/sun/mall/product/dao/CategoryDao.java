package com.sun.mall.product.dao;

import com.sun.mall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author Jianda Sun
 * @email jianda_sun@qq.com
 * @date 2022-10-26 17:57:00
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
