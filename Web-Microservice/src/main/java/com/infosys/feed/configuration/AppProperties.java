package com.infosys.feed.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "app")
public class AppProperties {
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
