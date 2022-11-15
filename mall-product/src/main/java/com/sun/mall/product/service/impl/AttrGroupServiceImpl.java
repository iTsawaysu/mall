package com.sun.mall.product.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.mall.common.utils.PageUtils;
import com.sun.mall.common.utils.Query;
import com.sun.mall.product.dao.AttrGroupDao;
import com.sun.mall.product.entity.AttrGroupEntity;
import com.sun.mall.product.service.AttrGroupService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author sun
 */
@Service("attrGroupService")
@SuppressWarnings("ALL")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Override
    public PageUtils queryAttrGroupsByCategoryId(Map<String, Object> params, Long catelogId) {
        LambdaQueryWrapper<AttrGroupEntity> wrapper = new LambdaQueryWrapper<>();
        if (catelogId != 0) {
            wrapper.eq(AttrGroupEntity::getCatelogId, catelogId);
        }

        String key = (String) params.get("key");
        if (StrUtil.isNotBlank(key)) {
            // select * from psm_attr_group where catelog_id = catelogId and (attr_group_id = key or attr_group_name like %key%)
            wrapper.and(one -> {
                one.eq(AttrGroupEntity::getAttrGroupId, key).or().like(AttrGroupEntity::getAttrGroupName, key);
            });
        }

        IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params), wrapper);
        return new PageUtils(page);
    }
}
