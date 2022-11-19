package com.sun.mall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sun.mall.common.utils.PageUtils;
import com.sun.mall.ware.entity.PurchaseEntity;
import com.sun.mall.ware.vo.MergeVo;

import java.util.List;
import java.util.Map;

/**
 * 采购信息
 *
 * @author Jianda Sun
 * @email jianda_sun@qq.com
 * @date 2022-10-26 21:06:58
 */
public interface PurchaseService extends IService<PurchaseEntity> {

    PageUtils queryPage(Map<String, Object> params);


    /**
     * 根据 params 条件查询展示 新建、已分配 状态的采购单
     */
    PageUtils queryUnreceivedPurchasePageByParams(Map<String, Object> params);

    /**
     * 合并采购需求到采购单（如果没有采购单则新建一个采购单再合并到里面）
     */
    void mergePurchaseDetailsToPurchaseOrder(MergeVo mergeVo);

    /**
     * 领取采购单
     */
    void receivedThePurchaseOrder(List<Long> purchaseOrderIdList);

}

