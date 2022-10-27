package com.sun.mall.order.dao;

import com.sun.mall.order.entity.RefundInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 退款信息
 * 
 * @author Jianda Sun
 * @email jianda_sun@qq.com
 * @date 2022-10-26 21:14:06
 */
@Mapper
public interface RefundInfoDao extends BaseMapper<RefundInfoEntity> {
	
}
