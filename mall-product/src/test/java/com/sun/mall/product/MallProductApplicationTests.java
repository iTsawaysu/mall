package com.sun.mall.product;

import cn.hutool.core.util.StrUtil;
import com.sun.mall.product.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class MallProductApplicationTests {

    @Resource
    private CategoryService categoryService;

    @Test
    public void contextLoads() {
        log.info("完整路径：{}", Arrays.asList(categoryService.getCompletePathByCategoryId(226L)));
        try {
            System.out.println(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            log.error("error: {}", e);
        }
    }

    @Test
    public void strJoinTest() {
        List<String> list = Arrays.asList("你好", "好好学习", "天天向上");
        System.out.println(list);
        String join = StrUtil.join(" - ", list);
        System.out.println(join);

        // [你好, 好好学习, 天天向上]
        // 你好 - 好好学习 - 天天向上
    }

}
