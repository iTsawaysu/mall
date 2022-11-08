package com.sun.mall.product;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MallProductApplicationTests {

    //@Autowired
    //private OSSClient ossClient;

    @Test
    public void contextLoads() {
        System.out.println("HelloWorld");
    }

    //@Test
    //public void uploadTest() {
    //    String bucketName = "mall-study-sun";
    //    String objectName = "test/sky.jpg";
    //    String filePath = "/Users/sun/Pictures/12.jpg";
    //
    //    ossClient.putObject(bucketName, objectName, new File(filePath));
    //}

}
