package com.sun.mall.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author sun
 */
@SpringBootApplication
@MapperScan("com.sun.mall.order.dao")
@EnableDiscoveryClient
public class MallOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(MallOrderApplication.class, args);
	}

}
