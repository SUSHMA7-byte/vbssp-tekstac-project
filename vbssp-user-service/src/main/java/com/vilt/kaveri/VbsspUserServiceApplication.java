package com.vilt.kaveri;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class VbsspUserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VbsspUserServiceApplication.class, args);
	}

}
