package com.infosys.feed.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import lombok.Data;
import lombok.NoArgsConstructor;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Data
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "app")
public class AppProperties {

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public Docket swaggerApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.infosys.feed.controller"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(new ApiInfoBuilder().version("1.0").title("Feed API").description("Documentation Feed API v1.0").build());
	}

	private String name;
	private List<Menu> topics = new ArrayList<>();

	@Data
	@NoArgsConstructor
	public static class Menu {
		private String name;
		private String description;
		private String title;
		private String path;
		private String type;
	}
}
