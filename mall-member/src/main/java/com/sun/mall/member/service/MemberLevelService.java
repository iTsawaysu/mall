package com.sun.mall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sun.mall.common.utils.PageUtils;
import com.sun.mall.member.entity.MemberLevelEntity;

import java.util.Map;

/**
 * 会员等级
 *
 * @author Jianda Sun
 * @email jianda_sun@qq.com
 * @date 2022-10-26 21:10:52
 */
public interface MemberLevelService extends IService<MemberLevelEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

