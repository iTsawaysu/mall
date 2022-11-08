package com.sun.mall.thirdparty;

import com.aliyun.oss.OSSClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MallThirdPartyApplicationTests {

	@Autowired
	private OSSClient ossClient;

	@Test
	public void contextLoads() throws FileNotFoundException {
		String bucketName = "mall-study-sun";
		String objectName = "test/lala.jpg";
		String filePath = "/Users/sun/Pictures/IMAGE.JPG";
		ossClient.putObject(bucketName, objectName, new FileInputStream(filePath));
	}

}
