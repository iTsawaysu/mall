package com.sun.mall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sun.mall.common.utils.PageUtils;
import com.sun.mall.member.entity.IntegrationChangeHistoryEntity;

import java.util.Map;

/**
 * 积分变化历史记录
 *
 * @author Jianda Sun
 * @email jianda_sun@qq.com
 * @date 2022-10-26 21:10:52
 */
public interface IntegrationChangeHistoryService extends IService<IntegrationChangeHistoryEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

