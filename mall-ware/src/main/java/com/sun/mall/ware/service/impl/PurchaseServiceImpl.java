package com.sun.mall.ware.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.mall.common.constant.WareConstants;
import com.sun.mall.common.utils.PageUtils;
import com.sun.mall.common.utils.Query;
import com.sun.mall.ware.dao.PurchaseDao;
import com.sun.mall.ware.entity.PurchaseDetailEntity;
import com.sun.mall.ware.entity.PurchaseEntity;
import com.sun.mall.ware.service.PurchaseDetailService;
import com.sun.mall.ware.service.PurchaseService;
import com.sun.mall.ware.vo.MergeVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author sun
 */
@Service("purchaseService")
@SuppressWarnings("ALL")
public class PurchaseServiceImpl extends ServiceImpl<PurchaseDao, PurchaseEntity> implements PurchaseService {

    @Resource
    private PurchaseDetailService purchaseDetailService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params),
                new QueryWrapper<PurchaseEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryUnreceivedPurchasePageByParams(Map<String, Object> params) {
        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params),
                new LambdaQueryWrapper<PurchaseEntity>()
                        // 0-新建；1-已分配
                        .eq(PurchaseEntity::getStatus, WareConstants.PurchaseStatusEnum.CREATED.getCode())
                        .or()
                        .eq(PurchaseEntity::getStatus, WareConstants.PurchaseStatusEnum.ASSIGNED.getCode())
        );
        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void mergePurchaseDetailsToPurchaseOrder(MergeVo mergeVo) {
        Long purchaseId = mergeVo.getPurchaseId();
        if (purchaseId == null) {
            PurchaseEntity purchaseEntity = new PurchaseEntity();
            purchaseEntity.setCreateTime(new Date());
            purchaseEntity.setUpdateTime(new Date());
            purchaseEntity.setStatus(WareConstants.PurchaseStatusEnum.CREATED.getCode());
            this.save(purchaseEntity);
            purchaseId = purchaseEntity.getId();
        }

        if (this.getById(purchaseId).getStatus().equals(WareConstants.PurchaseStatusEnum.CREATED.getCode()) || this.getById(purchaseId).getStatus().equals(WareConstants.PurchaseStatusEnum.ASSIGNED.getCode())) {
            Long finalPurchaseId = purchaseId;
            List<Long> items = mergeVo.getItems();
            List<PurchaseDetailEntity> purchaseDetailEntityList = items.stream()
                    .map(item -> {
                        PurchaseDetailEntity purchaseDetail = new PurchaseDetailEntity();
                        // 采购单 ID
                        purchaseDetail.setPurchaseId(finalPurchaseId);
                        // 采购商品 ID
                        purchaseDetail.setId(item);
                        purchaseDetail.setStatus(WareConstants.PurchaseDetailStatusEnum.ASSIGNED.getCode());
                        return purchaseDetail;
                    })
                    .collect(Collectors.toList());
            purchaseDetailService.updateBatchById(purchaseDetailEntityList);

            PurchaseEntity purchaseEntity = new PurchaseEntity();
            purchaseEntity.setId(purchaseId);
            purchaseEntity.setUpdateTime(new Date());
            this.updateById(purchaseEntity);
        }
    }

    @Transactional
    @Override
    public void receivedThePurchaseOrder(List<Long> purchaseOrderIdList) {
        // 1. 确认当前采购单状态为 新建 or 已分配
        List<PurchaseEntity> purchaseEntityList = purchaseOrderIdList
                .stream()
                .map(this::getById)
                .filter(purchaseEntity -> purchaseEntity.getStatus().equals(WareConstants.PurchaseStatusEnum.CREATED.getCode()) || purchaseEntity.getStatus().equals(WareConstants.PurchaseStatusEnum.ASSIGNED.getCode()))
                .collect(Collectors.toList());

        if (!purchaseEntityList.isEmpty() && ObjectUtil.isNotNull(purchaseEntityList)) {
            // 2. 改变采购单的状态
            purchaseEntityList.forEach(purchaseEntity -> {
                purchaseEntity.setStatus(WareConstants.PurchaseStatusEnum.RECEIVED.getCode());
                purchaseEntity.setUpdateTime(new Date());
            });
            this.updateBatchById(purchaseEntityList);

            // 3. 改变采购项的状态
            purchaseEntityList.forEach(purchaseEntity -> {
                List<PurchaseDetailEntity> purchaseDetailList = getPurchaseDetailsByPurchaseId(purchaseEntity.getId());
                purchaseDetailList = purchaseDetailList
                        .stream()
                        .map(purchaseDetail -> {
                            purchaseDetail.setStatus(WareConstants.PurchaseDetailStatusEnum.BUYING.getCode());
                            return purchaseDetail;
                        })
                        .collect(Collectors.toList());
                purchaseDetailService.updateBatchById(purchaseDetailList);
            });
        }
        throw new RuntimeException(WareConstants.PurchaseDetailStatusEnum.HAS_ERROR.getMsg());
    }

    private List<PurchaseDetailEntity> getPurchaseDetailsByPurchaseId(Long id) {
        return purchaseDetailService.lambdaQuery().eq(PurchaseDetailEntity::getPurchaseId, id).list();
    }


}
