package com.infosys.feed.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.infosys.feed.entity.Feed;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FeedService {
	public String url = "http://FEEDAPI/rss/";

	@Autowired
	RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod ="getFeedsByTopicBack" )
	public List<Feed> getAllFeedsByTopic(String topicType) {
		log.info("Find All feeds by {}", topicType);
		return restTemplate.getForObject(url +"feeds/"+ topicType , List.class);
	}

	public List<Feed> getFeedsByTopicBack(String topicType){	
		return new ArrayList<Feed>();
	}
}
