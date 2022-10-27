package com.sun.mall.member.feign;

import com.sun.mall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author Sun Jianda
 * @Date 2022/10/26
 */

@Component
@FeignClient("mall-coupon")
public interface CouponFeignService {
    /**
     *  1. 服务启动，会自动扫描 @EnableFeignClients 注解指定的包；
     *  2. 通过 @FeignClient("服务名") 注解，在 注册中心 中找到对应的服务；
     *  3. 最后再调用 该请求路径所对应的 Controller 方法。
     */
    @GetMapping("/coupon/coupon/test")
    public R test();
}
