package com.infosys.rss.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.infosys.rss.model.Feed;

public interface FeedRepository extends MongoRepository<Feed, String> {
	List<Feed> findByFeedType(String topic);
}
