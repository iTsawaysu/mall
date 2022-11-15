package com.sun.mall.product.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.mall.common.constant.ProductConstants;
import com.sun.mall.common.utils.PageUtils;
import com.sun.mall.common.utils.Query;
import com.sun.mall.product.dao.AttrAttrgroupRelationDao;
import com.sun.mall.product.dao.AttrDao;
import com.sun.mall.product.entity.AttrAttrgroupRelationEntity;
import com.sun.mall.product.entity.AttrEntity;
import com.sun.mall.product.entity.AttrGroupEntity;
import com.sun.mall.product.entity.CategoryEntity;
import com.sun.mall.product.service.AttrAttrgroupRelationService;
import com.sun.mall.product.service.AttrGroupService;
import com.sun.mall.product.service.AttrService;
import com.sun.mall.product.service.CategoryService;
import com.sun.mall.vo.AttrGroupRelationVo;
import com.sun.mall.vo.AttrResponseVo;
import com.sun.mall.vo.AttrVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author sun
 */
@Service("attrService")
@SuppressWarnings("ALL")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Resource
    private AttrAttrgroupRelationService attrAttrgroupRelationService;

    @Resource
    private AttrAttrgroupRelationDao attrAttrgroupRelationDao;

    @Resource
    private AttrGroupService attrGroupService;

    @Resource
    private CategoryService categoryService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );
        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void saveAttr(AttrVo attrVo) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtil.copyProperties(attrVo, attrEntity);
        this.save(attrEntity);

        if (attrVo.getAttrType().equals(ProductConstants.AttrEnum.ATTR_TYPE_BASE.getCode())) {
            AttrAttrgroupRelationEntity relation = new AttrAttrgroupRelationEntity();
            relation.setAttrId(attrEntity.getAttrId());
            relation.setAttrGroupId(attrVo.getAttrGroupId());
            relation.setAttrSort(0);
            attrAttrgroupRelationService.save(relation);
        }
    }

    @Override
    public PageUtils queryBaseAttrPage(Map<String, Object> params, String attrType, Long catelogId) {
        // 请求路径中的 {attrType} 为 base，则查询条件为 attrType = 1；否则，查询条件为 attrType = 0。
        LambdaQueryWrapper<AttrEntity> wrapper = new LambdaQueryWrapper<AttrEntity>()
                .eq(AttrEntity::getAttrType, "base".equals(attrType)
                        ? ProductConstants.AttrEnum.ATTR_TYPE_BASE.getCode()
                        : ProductConstants.AttrEnum.ATTR_TYPE_SALE.getCode());
        if (catelogId != 0) {
            wrapper.eq(AttrEntity::getCatelogId, catelogId);
        }
        String key = (String) params.get("key");
        if (StrUtil.isNotBlank(key)) {
            wrapper.and(one -> {
                one.eq(AttrEntity::getAttrId, key).or().like(AttrEntity::getAttrName, key);
            });
        }
        IPage<AttrEntity> page = this.page(new Query<AttrEntity>().getPage(params), wrapper);
        PageUtils pageUtils = new PageUtils(page);
        List<AttrResponseVo> attrResponseVoList = page.getRecords()
                .stream()
                .map(attrEntity -> {
                    AttrResponseVo attrResponseVo = new AttrResponseVo();
                    BeanUtil.copyProperties(attrEntity, attrResponseVo);

                    if ("base".equalsIgnoreCase(attrType)) {
                        AttrAttrgroupRelationEntity relation = attrAttrgroupRelationService
                                .lambdaQuery()
                                .eq(AttrAttrgroupRelationEntity::getAttrId, attrResponseVo.getAttrId())
                                .one();
                        if (ObjectUtil.isNotNull(relation)) {
                            AttrGroupEntity attrGroup = attrGroupService.getById(relation.getAttrGroupId());
                            attrResponseVo.setGroupName(attrGroup.getAttrGroupName());
                        }
                    }

                    CategoryEntity category = categoryService.getById(attrResponseVo.getCatelogId());
                    if (ObjectUtil.isNotNull(category)) {
                        attrResponseVo.setCatelogName(category.getName());
                    }
                    return attrResponseVo;
                })
                .collect(Collectors.toList());
        pageUtils.setList(attrResponseVoList);
        return pageUtils;
    }

    @Override
    public AttrResponseVo getAttrDetailByAttrId(Long attrId) {
        AttrEntity attrEntity = getById(attrId);
        AttrResponseVo attrResponseVo = new AttrResponseVo();
        BeanUtil.copyProperties(attrEntity, attrResponseVo);

        // 设置分组信息
        if (attrEntity.getAttrType().equals(ProductConstants.AttrEnum.ATTR_TYPE_BASE.getCode())) {
            AttrAttrgroupRelationEntity relation = attrAttrgroupRelationService
                    .lambdaQuery()
                    .eq(AttrAttrgroupRelationEntity::getAttrId, attrId)
                    .one();
            if (ObjectUtil.isNotNull(relation)) {
                attrResponseVo.setAttrGroupId(relation.getAttrGroupId());
                AttrGroupEntity attrGroup = attrGroupService.getById(relation.getAttrGroupId());
                if (ObjectUtil.isNotNull(attrGroup)) {
                    attrResponseVo.setGroupName(attrGroup.getAttrGroupName());
                }
            }
        }

        // 设置分类信息
        Long catelogId = attrEntity.getCatelogId();
        Long[] path = categoryService.getCompletePathByCategoryId(catelogId);
        attrResponseVo.setCatelogPath(path);
        CategoryEntity categoryEntity = categoryService.getById(catelogId);
        if (ObjectUtil.isNotNull(categoryEntity)) {
            attrResponseVo.setCatelogName(categoryEntity.getName());
        }

        return attrResponseVo;
    }

    @Transactional
    @Override
    public void updateAttr(AttrVo attrVo) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtil.copyProperties(attrVo, attrEntity);
        this.updateById(attrEntity);

        if (attrEntity.getAttrType().equals(ProductConstants.AttrEnum.ATTR_TYPE_BASE.getCode())) {
            AttrAttrgroupRelationEntity relation = new AttrAttrgroupRelationEntity();
            relation.setAttrId(attrVo.getAttrId());
            relation.setAttrGroupId(attrVo.getAttrGroupId());
            Integer count = attrAttrgroupRelationService.lambdaQuery().eq(AttrAttrgroupRelationEntity::getAttrId, attrVo.getAttrId()).count();
            if (count > 0) {
                attrAttrgroupRelationService.update(
                        relation,
                        new LambdaUpdateWrapper<AttrAttrgroupRelationEntity>().eq(AttrAttrgroupRelationEntity::getAttrId, attrVo.getAttrId())
                );
            } else {
                attrAttrgroupRelationService.save(relation);
            }
        }
    }

    @Override
    public List<AttrEntity> getAssociatedAttrByGroupId(Long groupId) {
        return attrAttrgroupRelationService
                // 根据 groupId 获取到 List<AttrAttrgroupRelationEntity>
                .lambdaQuery()
                .eq(AttrAttrgroupRelationEntity::getAttrGroupId, groupId)
                .list()
                .stream()
                // 遍历 List<AttrAttrgroupRelationEntity> 获取到 List<AttrEntity>
                .map(relation -> this.getById(relation.getAttrId()))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteTheRelations(List<AttrGroupRelationVo> attrGroupRelationVoList) {
        List<AttrAttrgroupRelationEntity> relationList = attrGroupRelationVoList
                .stream()
                .map(item -> {
                    AttrAttrgroupRelationEntity relation = new AttrAttrgroupRelationEntity();
                    BeanUtil.copyProperties(item, relation);
                    return relation;
                })
                .collect(Collectors.toList());

        // DELETE FROM psm_attr_attrgroup_relation WHERE (attr_id = ? AND attr_group_id = ?) OR (attr_id = ? AND attr_group_id = ?) OR ...
        attrAttrgroupRelationDao.deleteBatchByRelations(relationList);
    }
}
