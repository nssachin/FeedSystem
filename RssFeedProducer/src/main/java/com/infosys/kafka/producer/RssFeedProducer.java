package com.infosys.kafka.producer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;

import com.infosys.kafka.model.Feed;
import com.infosys.kafka.model.FeedMessage;

@Component
public class RssFeedProducer implements ItemWriter<Feed> {

	private static final Logger LOGGER = LoggerFactory.getLogger(RssFeedProducer.class);

	@Autowired
	private KafkaTemplate<String, FeedMessage> kafkaTemplate;

	@Value("${spring.kafka.template.default-topic}")
	private String topic;

	@Override
	public void write(List<? extends Feed> items) throws Exception {
		LOGGER.info("************* Received the information of {} Rss Feeds");
		items.forEach(feed -> {
			feed.getEntries().forEach((key, value) -> {
				value.forEach(message -> send(key, message));
			});
		});
	}

	private void send(String key, FeedMessage data) {
		data.setFeedType(key);
		Message<FeedMessage> message = MessageBuilder.withPayload(data)
				.setHeader(KafkaHeaders.MESSAGE_KEY, key)
				.setHeader(KafkaHeaders.TOPIC, topic)
				.build();
		ListenableFuture<SendResult<String, FeedMessage>> future = kafkaTemplate.send(message);
		future.addCallback(onSuccess(message), onFailure());
	}

	private FailureCallback onFailure() {
		return new FailureCallback() {

			@Override
			public void onFailure(Throwable ex) {
				LOGGER.error("unable to send message= ");
			}
		};
	}

	private SuccessCallback<SendResult<String, FeedMessage>> onSuccess(Message<FeedMessage> message) {
		return new SuccessCallback<SendResult<String, FeedMessage>>() {
			@Override
			public void onSuccess(SendResult<String, FeedMessage> result) {
				LOGGER.info("sent message= " + message + " with offset= " + result.getRecordMetadata().offset() +
						"for Partision= " + result.getRecordMetadata().partition());
			}
		};
	}

}
