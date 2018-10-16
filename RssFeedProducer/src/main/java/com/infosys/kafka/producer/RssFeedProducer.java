package com.infosys.kafka.producer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.infosys.kafka.model.Feed;
import com.infosys.kafka.model.FeedMessage;

@Component
public class RssFeedProducer implements ItemWriter<Feed> {

	private static final Logger LOGGER = LoggerFactory.getLogger(RssFeedProducer.class);

	@Autowired
	private KafkaTemplate<String, FeedMessage> kafkaTemplate;

	@Override
	public void write(List<? extends Feed> items) throws Exception {
		LOGGER.info("************* Received the information of {} Rss Feeds");
		items.forEach(feed -> {
			feed.getEntries().forEach((key, value) -> {
				value.forEach(message -> send(key, message));
			});
		});
	}

	private void send(String topic, FeedMessage data) {
		LOGGER.info("sending data='{}' to topic='{}'", data, topic);
		Message<FeedMessage> message = MessageBuilder.withPayload(data)
				.setHeader(KafkaHeaders.TOPIC, topic)
				.build();
		kafkaTemplate.send(message);
	}

}
