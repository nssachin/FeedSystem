package com.infosys.kafka.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.infosys.kafka.model.FeedMessage;

@Repository
public interface FeedRepository extends MongoRepository<FeedMessage, String> {

}
