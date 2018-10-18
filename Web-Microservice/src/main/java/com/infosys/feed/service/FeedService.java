package com.infosys.feed.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.infosys.feed.entity.Feed;
import com.infosys.feed.repository.FeedRepository;

@Service
public class FeedService {
	private static final Logger LOGGER = LoggerFactory.getLogger(FeedService.class);

	@Autowired
	FeedRepository feedRepository;

	@Value("${db.collection.info}")
	String collectionName;

	public List<Feed> getAllFeedsByTopic(String topic) {
		LOGGER.info("Find All feeds by {}", topic);
		return feedRepository.findByTopic(topic);
	}

	public String getCollectionName() {
		return collectionName;
	}
}
