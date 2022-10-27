package com.sun.mall.order.dao;

import com.sun.mall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author Jianda Sun
 * @email jianda_sun@qq.com
 * @date 2022-10-26 21:14:06
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
