package com.spring.cloud.k8s.poc.microservice_a;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
public class MicroserviceAApplication {
	public static void main(String[] args) {
		SpringApplication.run(MicroserviceAApplication.class, args);
	}
}
