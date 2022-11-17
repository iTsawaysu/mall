package com.sun.mall.product.feign;

import com.sun.mall.common.dto.SkuReductionDTO;
import com.sun.mall.common.dto.SpuBoundsDTO;
import com.sun.mall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author sun
 */
@Component
@FeignClient("mall-coupon")
public interface CouponFeignService {
    /**
     * <p> 1. 服务启动，自动扫描主启动类上 @EnableFeignClients 注解所指向的包； </p>
     * <p> PS：即使不指定 basePackages，该注解也会扫描到标注 @FeignClient 注解的接口。 </p>
     * <p> 2. 在该包中找到标注 @FeignClient 的接口，该注解会在注册中心中找到对应的服务； </p>
     * <p> 3. 当在 Controller 中调用该接口的方法时，该服务会进行处理。 </p>
     *
     * <p> save(@RequestBody SpuBoundsDTO spuBoundsDTO)</p>
     * <p> @RequestBody 将这个对象转化为 JSON，找到 mall-coupon 服务；发送 /coupon/spuBounds/save 请求；将上一步转换的 JSON 放在请求体发送数据。</p>
     *
     * <p>save(@RequestBody SpuBoundsEntity spuBounds)</p>
     * <p>mall-coupon 服务接收到请求，请求体中的 JSON 数据会被 @RequestBody 转换为 SpuBoundsEntity；只要 JSON 数据是兼容的，不需要使用同一个 POJO。</p>
     */
    @PostMapping("/coupon/spuBounds/save")
    R save(@RequestBody SpuBoundsDTO spuBoundsDTO);

    @PostMapping("/coupon/skuFullReduction/saveSkuReduction")
    R saveSkuReduction(@RequestBody SkuReductionDTO skuReductionDTO);
}
