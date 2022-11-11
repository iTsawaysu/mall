package com.sun.mall.product;

import com.sun.mall.product.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Arrays;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class MallProductApplicationTests {

    @Resource
    private CategoryService categoryService;

    @Test
    public void contextLoads() {
        log.info("完整路径：{}", Arrays.asList(categoryService.getCompletePathByCategoryId(226L)));
    }

}
