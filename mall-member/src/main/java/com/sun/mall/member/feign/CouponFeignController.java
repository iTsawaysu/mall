package com.sun.mall.member.feign;

import com.sun.mall.common.utils.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author Sun Jianda
 * @Date 2022/10/26
 */

@RestController
@RequestMapping("/member/coupon")
public class CouponFeignController {

    @Resource
    private CouponFeignService couponFeignService;

    @GetMapping("/test")
    public R test() {
        return couponFeignService.test();
    }
}
