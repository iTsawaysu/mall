package com.sun.mall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sun.mall.common.utils.PageUtils;
import com.sun.mall.member.entity.MemberLoginLogEntity;

import java.util.Map;

/**
 * 会员登录记录
 *
 * @author Jianda Sun
 * @email jianda_sun@qq.com
 * @date 2022-10-26 21:10:52
 */
public interface MemberLoginLogService extends IService<MemberLoginLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

