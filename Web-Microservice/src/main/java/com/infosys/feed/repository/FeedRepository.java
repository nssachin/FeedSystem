package com.infosys.feed.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.infosys.feed.entity.Feed;
import java.lang.String;
import java.util.List;

@Repository
public interface FeedRepository extends MongoRepository<Feed, String> {
	List<Feed> findByTopic(String topic);
}
