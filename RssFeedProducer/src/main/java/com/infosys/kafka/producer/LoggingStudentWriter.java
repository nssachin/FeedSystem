package com.infosys.kafka.producer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import com.infosys.kafka.model.Feed;

@Component
public class LoggingStudentWriter implements ItemWriter<Feed>{
	private static final Logger LOGGER = LoggerFactory.getLogger(LoggingStudentWriter.class);

	@Override
	public void write(List<? extends Feed> items) throws Exception {
		LOGGER.info("************* Received the information of {} Rss Feeds");
		items.forEach(feed -> {
			LOGGER.info("Received the information of a Rss Feed: {}", feed.getEntries().size());
			
			feed.getEntries().forEach((key, value) -> {
				LOGGER.info("Topic: {}", key);
				LOGGER.info("Size: {}", value.size());
			});
		});
	}

}
