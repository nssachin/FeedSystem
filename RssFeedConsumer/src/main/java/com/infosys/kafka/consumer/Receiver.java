package com.infosys.kafka.consumer;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import com.infosys.kafka.model.FeedMessage;
import com.infosys.kafka.repository.FeedRepository;


public class Receiver {

	@Autowired
	public FeedRepository respository;

	private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

	@KafkaListener(topics="infosys.info", containerFactory="kafkaListenerContainerFactory" )
	public void receiveInfo(List<FeedMessage> data) {
		LOGGER.info("start of batch receive");
		List<FeedMessage> feedList = new ArrayList<FeedMessage>();
		data.forEach(feed -> {
			feedList.add(feed);
		});
		LOGGER.info("received message='{}' to persist to MONGO", feedList.size());
		save(feedList);
		LOGGER.info("end of batch receive");
	}

	private void save(List<FeedMessage> entites) {
		LOGGER.info("## Save to Mongo DB - START ##");
		respository.saveAll(entites);
		LOGGER.info("## Save to Mongo DB - END ##");
	}
}
