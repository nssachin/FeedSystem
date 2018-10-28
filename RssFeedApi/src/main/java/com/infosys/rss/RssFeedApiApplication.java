package com.infosys.rss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RssFeedApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RssFeedApiApplication.class, args);
	}
}
