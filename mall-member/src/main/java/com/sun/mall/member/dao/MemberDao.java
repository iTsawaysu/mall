package com.sun.mall.member.dao;

import com.sun.mall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author Jianda Sun
 * @email jianda_sun@qq.com
 * @date 2022-10-26 21:10:52
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
