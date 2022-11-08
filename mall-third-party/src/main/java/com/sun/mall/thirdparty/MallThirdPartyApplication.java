package com.sun.mall.thirdparty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author sun
 */

@SpringBootApplication
@EnableDiscoveryClient
public class MallThirdPartyApplication {

	public static void main(String[] args) {
		SpringApplication.run(MallThirdPartyApplication.class, args);
	}

}
