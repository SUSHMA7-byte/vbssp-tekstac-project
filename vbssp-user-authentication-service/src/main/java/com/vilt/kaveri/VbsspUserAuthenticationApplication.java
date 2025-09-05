package com.vilt.kaveri;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.vilt.kaveri.feign")
@EnableDiscoveryClient
public class VbsspUserAuthenticationApplication {

	public static void main(String[] args) {
		SpringApplication.run(VbsspUserAuthenticationApplication.class, args);
	}

}
