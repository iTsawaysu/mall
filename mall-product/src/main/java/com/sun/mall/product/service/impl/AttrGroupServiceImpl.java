package com.sun.mall.product.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.mall.common.utils.PageUtils;
import com.sun.mall.common.utils.Query;
import com.sun.mall.product.dao.AttrGroupDao;
import com.sun.mall.product.entity.AttrGroupEntity;
import com.sun.mall.product.service.AttrGroupService;
import com.sun.mall.product.service.AttrService;
import com.sun.mall.product.vo.AttrGroupWithAttrsVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author sun
 */
@Service("attrGroupService")
@SuppressWarnings("ALL")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Resource
    private AttrService attrService;

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

    @Override
    public List<AttrGroupWithAttrsVo> getAttrGroupWithAttrsByCategoryId(Long catelogId) {
        List<AttrGroupEntity> attrGroupList = this.lambdaQuery().eq(AttrGroupEntity::getCatelogId, catelogId).list();
        return attrGroupList
                .stream()
                .map(attrGroup -> {
                    AttrGroupWithAttrsVo attrGroupWithAttrsVo = new AttrGroupWithAttrsVo();
                    BeanUtil.copyProperties(attrGroup, attrGroupWithAttrsVo);
                    // 根据 groupId 查询当前分组关联的所有属性
                    attrGroupWithAttrsVo.setAttrs(attrService.getAssociatedAttrByGroupId(attrGroup.getAttrGroupId()));
                    return attrGroupWithAttrsVo;
                })
                .collect(Collectors.toList());
    }
}
