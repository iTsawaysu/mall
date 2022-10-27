package com.sun.mall.coupon.dao;

import com.sun.mall.coupon.entity.SeckillSessionEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 秒杀活动场次
 * 
 * @author Jianda Sun
 * @email jianda_sun@qq.com
 * @date 2022-10-26 21:09:46
 */
@Mapper
public interface SeckillSessionDao extends BaseMapper<SeckillSessionEntity> {
	
}
