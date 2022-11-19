package com.sun.mall.ware.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.mall.common.utils.PageUtils;
import com.sun.mall.common.utils.Query;
import com.sun.mall.ware.dao.WareInfoDao;
import com.sun.mall.ware.entity.WareInfoEntity;
import com.sun.mall.ware.service.WareInfoService;
import org.springframework.stereotype.Service;

import java.util.Map;


/**
 * @author sun
 */
@Service("wareInfoService")
public class WareInfoServiceImpl extends ServiceImpl<WareInfoDao, WareInfoEntity> implements WareInfoService {

    @Override
    public PageUtils queryWareInfoPageByParams(Map<String, Object> params) {
        LambdaQueryWrapper<WareInfoEntity> wrapper = new LambdaQueryWrapper<>();
        String key = (String) params.get("key");
        if (StrUtil.isNotBlank(key)) {
            wrapper.eq(WareInfoEntity::getId, key)
                    .or().like(WareInfoEntity::getName, key)
                    .or().like(WareInfoEntity::getAddress, key)
                    .or().like(WareInfoEntity::getAreacode, key);
        }
        IPage<WareInfoEntity> page = this.page(new Query<WareInfoEntity>().getPage(params), wrapper);
        return new PageUtils(page);
    }
}
