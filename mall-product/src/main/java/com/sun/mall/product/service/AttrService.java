package com.sun.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sun.mall.common.utils.PageUtils;
import com.sun.mall.product.entity.AttrEntity;
import com.sun.mall.product.vo.AttrGroupRelationVo;
import com.sun.mall.product.vo.AttrResponseVo;
import com.sun.mall.product.vo.AttrVo;

import java.util.List;
import java.util.Map;

/**
 * 商品属性
 *
 * @author Jianda Sun
 * @email jianda_sun@qq.com
 * @date 2022-10-26 17:57:00
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 新增属性的同时信息到 属性&属性分组关联表 中
     */
    void saveAttr(AttrVo attrVo);

    /**
     * 获取分类的 基本属性 or 销售属性
     */
    PageUtils queryBaseAttrOrSaleAttrPage(Map<String, Object> params, String attrType, Long catelogId);

    /**
     * 通过 属性ID 获取属性的详细信息
     */
    AttrResponseVo getAttrDetailByAttrId(Long attrId);

    /**
     * 修改基本属性
     */
    void updateAttr(AttrVo attrVo);

    /**
     * 根据 groupId 查询 当前分组关联的所有属性
     */
    List<AttrEntity> getAssociatedAttrByGroupId(Long groupId);

    /**
     * 删除 属性分组 和 属性 间的关联
     */
    void deleteTheRelations(List<AttrGroupRelationVo> attrGroupRelationVoList);

    /**
     * 根据 groupId 查询 当前分组未关联的所有属性
     */
    PageUtils getNotAssociatedAttrByGroupId(Long groupId, Map<String, Object> params);
}
