package com.infosys.rss.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.infosys.rss.model.Feed;
import com.infosys.rss.service.FeedService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class FeedController {

	@Autowired
	FeedService service;

	@PostMapping("/rss/feeds")
	public void saveFeed(@RequestBody List<Feed> feeds) {
		service.saveAll(feeds);
	}

	@GetMapping("/rss/feeds/{name}")
	public List<Feed> getFeedsByType(@PathVariable String topicType) {
		log.info("Get RSS feeds by type");
		return service.findByTopic(topicType);
	}

	@GetMapping("/rss/feeds")
	public List<Feed> getAllFeeds() {
		log.info("Get all RSS feeds");
		return service.findAll();
	}

	@GetMapping("/rss/ping")
	public String ping() {
		return "pong";
	}
}
