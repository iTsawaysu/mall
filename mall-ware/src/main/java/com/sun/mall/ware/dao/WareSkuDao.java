package com.sun.mall.ware.dao;

import com.sun.mall.ware.entity.WareSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品库存
 * 
 * @author Jianda Sun
 * @email jianda_sun@qq.com
 * @date 2022-10-26 21:06:58
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {
	
}
