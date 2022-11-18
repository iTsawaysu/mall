package com.sun.mall.coupon.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.mall.common.dto.MemberPrice;
import com.sun.mall.common.dto.SkuReductionDTO;
import com.sun.mall.common.utils.PageUtils;
import com.sun.mall.common.utils.Query;
import com.sun.mall.coupon.dao.SkuFullReductionDao;
import com.sun.mall.coupon.entity.MemberPriceEntity;
import com.sun.mall.coupon.entity.SkuFullReductionEntity;
import com.sun.mall.coupon.entity.SkuLadderEntity;
import com.sun.mall.coupon.service.MemberPriceService;
import com.sun.mall.coupon.service.SkuFullReductionService;
import com.sun.mall.coupon.service.SkuLadderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author sun
 */
@Service("skuFullReductionService")
public class SkuFullReductionServiceImpl extends ServiceImpl<SkuFullReductionDao, SkuFullReductionEntity> implements SkuFullReductionService {

    @Resource
    private SkuLadderService skuLadderService;

    @Resource
    private MemberPriceService memberPriceService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuFullReductionEntity> page = this.page(
                new Query<SkuFullReductionEntity>().getPage(params),
                new QueryWrapper<SkuFullReductionEntity>()
        );

        return new PageUtils(page);
    }

    // TODO 待完善
    @Transactional
    @Override
    public void saveSkuReduction(SkuReductionDTO skuReductionDTO) {
        // 满减打折、会员价 `sms_sku_ladder`
        SkuLadderEntity skuLadderEntity = new SkuLadderEntity();
        skuLadderEntity.setSkuId(skuReductionDTO.getSkuId());
        skuLadderEntity.setFullCount(skuReductionDTO.getFullCount());
        skuLadderEntity.setDiscount(skuReductionDTO.getDiscount());
        skuLadderEntity.setAddOther(skuReductionDTO.getCountStatus());
        if (skuLadderEntity.getFullCount() > 0) {
            skuLadderService.save(skuLadderEntity);
        }

        // 满减信息 `sms_sku_full_reduction`
        SkuFullReductionEntity skuFullReductionEntity = new SkuFullReductionEntity();
        BeanUtil.copyProperties(skuReductionDTO, skuFullReductionEntity);
        if (skuFullReductionEntity.getFullPrice().compareTo(BigDecimal.ZERO) == 1) {
            this.save(skuFullReductionEntity);
        }

        // 会员价格 `sms_member_price`
        List<MemberPrice> memberPriceList = skuReductionDTO.getMemberPrice();
        List<MemberPriceEntity> memberPriceEntityList = memberPriceList
                .stream()
                .filter(memberPrice -> memberPrice.getPrice().compareTo(BigDecimal.ZERO) == 1)
                .map(memberPrice -> {
                    MemberPriceEntity memberPriceEntity = new MemberPriceEntity();
                    memberPriceEntity.setSkuId(skuReductionDTO.getSkuId());
                    memberPriceEntity.setMemberLevelId(memberPrice.getId());
                    memberPriceEntity.setMemberPrice(memberPrice.getPrice());
                    memberPriceEntity.setMemberLevelName(memberPrice.getName());
                    memberPriceEntity.setAddOther(1);
                    return memberPriceEntity;
                }).collect(Collectors.toList());
        memberPriceService.saveBatch(memberPriceEntityList);
    }

}
