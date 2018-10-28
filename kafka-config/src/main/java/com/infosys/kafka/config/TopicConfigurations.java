package com.infosys.kafka.config;

import java.util.List;
import java.util.Optional;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "kafka")
public class TopicConfigurations {

	public List<TopicConfiguration> topics;

	public Optional<List<TopicConfiguration>> getTopics() {
		return Optional.ofNullable(topics);
	}

	@Data
	static class TopicConfiguration {
		private String name;
		private Integer numPartitions = 1;
		private Short replicationFactor = 1;

		NewTopic toNewTopic() {
			return new NewTopic(this.name, this.numPartitions, this.replicationFactor);
		}
	}
}
