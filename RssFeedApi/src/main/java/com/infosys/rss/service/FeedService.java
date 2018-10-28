package com.infosys.rss.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.infosys.rss.model.Feed;
import com.infosys.rss.repository.FeedRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FeedService {

	@Autowired
	FeedRepository feedRepository;

	@Value("${db.collection.info}")
	String collectionName;
	
	public List<Feed> findAll() {
		return feedRepository.findAll();
	}

	public List<Feed> findByTopic(String topic) {
		log.info("Find All feeds by {}", topic);
		return feedRepository.findByTopic(topic);
	}

	public List<Feed> saveAll(List<Feed> entities) {
		return feedRepository.saveAll(entities);
	}

	public String getCollectionName() {
		return collectionName;
	}
}
