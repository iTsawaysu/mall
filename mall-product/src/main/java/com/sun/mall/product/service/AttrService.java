package com.sun.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sun.mall.common.utils.PageUtils;
import com.sun.mall.product.entity.AttrEntity;
import com.sun.mall.vo.AttrGroupRelationVo;
import com.sun.mall.vo.AttrResponseVo;
import com.sun.mall.vo.AttrVo;

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

    void saveAttr(AttrVo attrVo);

    PageUtils queryBaseAttrPage(Map<String, Object> params, String attrType, Long catelogId);

    AttrResponseVo getAttrDetailByAttrId(Long attrId);

    void updateAttr(AttrVo attrVo);

    List<AttrEntity> getAssociatedAttrByGroupId(Long groupId);

    void deleteTheRelations(List<AttrGroupRelationVo> attrGroupRelationVoList);

    PageUtils getNotAssociatedAttrByGroupId(Long groupId, Map<String, Object> params);
}
