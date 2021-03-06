package com.infosys.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.web.client.RestTemplate;

import com.infosys.kafka.consumer.Receiver;
import com.infosys.kafka.model.FeedMessage;

@Configuration
@EnableKafka
public class BatchConfiguration {


	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

	@Bean
	public Map<String, Object> consumerConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "infosys");
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		// maximum records per poll
		props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "10");
		return props;
	}

	@Bean
	public ConsumerFactory<String, FeedMessage> consumerFactory() {
		return new DefaultKafkaConsumerFactory<>(consumerConfigs(),
				new StringDeserializer(),
				new JsonDeserializer<>(FeedMessage.class));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, FeedMessage> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, FeedMessage> factory =
				new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		factory.setBatchListener(true);
		return factory;
	}

	@Bean
	public Receiver receiver() {
		return new Receiver();
	}
	
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
