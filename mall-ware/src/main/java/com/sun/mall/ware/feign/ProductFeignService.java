package com.sun.mall.ware.feign;

import com.sun.mall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author sun
 */

@Component
@FeignClient("mall-product")
public interface ProductFeignService {


    @GetMapping("/product/skuInfo/info/{skuId}")
    R info(@PathVariable("skuId") Long skuId);

}
