package com.sun.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sun.mall.common.utils.PageUtils;
import com.sun.mall.product.entity.SpuInfoEntity;
import com.sun.mall.product.vo.SpuSaveVo;

import java.util.Map;

/**
 * spu信息
 *
 * @author Jianda Sun
 * @email jianda_sun@qq.com
 * @date 2022-10-26 17:57:00
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    void saveSpuInfo(SpuSaveVo spuSaveVo);

    PageUtils querySpuInfoPageByParams(Map<String, Object> params);
}

