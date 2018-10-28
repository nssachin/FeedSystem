package com.infosys.kafka.config;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.web.context.support.GenericWebApplicationContext;

import com.infosys.kafka.config.TopicConfigurations.TopicConfiguration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class TopicAdministrator {

	private final TopicConfigurations configurations;
	private final GenericWebApplicationContext context;

	public TopicAdministrator(TopicConfigurations configurations,
			GenericWebApplicationContext context) {
		log.info("****************** CALLED CONSTRUCTOR");
		this.configurations = configurations;
		this.context = context;
	}

	@EventListener
	public void onRefreshScopeRefreshed(final RefreshScopeRefreshedEvent event) {
		log.info("##### Called referesh");
	    getClass();
	}
	
	@PostConstruct
	public void createTopics() {
		log.info("############# CALLED post construct");
		configurations
		.getTopics()
		.ifPresent(this::initializeBeans);
	}

	/**
	 * Loops and create topic beans
	 * @param topics
	 */
	private void initializeBeans(List<TopicConfiguration> topics) {
		log.info("Configuring {} topics", topics.size());
		topics.forEach(topic -> {
			log.info("topic={}, numOfPartisions={}, replicationValue={}",
					topic.getName(),
					topic.getNumPartitions(),
					topic.getReplicationFactor());
			context.registerBean(topic.getName(), NewTopic.class, topic::toNewTopic);
		});
	}

}
