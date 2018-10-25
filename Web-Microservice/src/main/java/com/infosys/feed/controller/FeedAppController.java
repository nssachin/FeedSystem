package com.infosys.feed.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.infosys.feed.configuration.AppProperties;
import com.infosys.feed.service.FeedService;

@Controller
public class FeedAppController {
	private static final String TOPIC_LIST = "topic-list";

	private static final Logger LOGGER = LoggerFactory.getLogger(FeedAppController.class);

	private AppProperties properties;
	private FeedService service;

	@Autowired
	public void setAppProperties(AppProperties properties) {
		this.properties = properties;
	}

	@Autowired
	public void setFeedService(FeedService service) {
		this.service = service;
	}

	@RequestMapping("/")
	public String index(Model model) {
		LOGGER.info("RSS Welcome page!");
		model.addAttribute("topics", properties.getTopics());
		return "index";
	}

	@RequestMapping("/info")
	public String getGenralFeed(@RequestParam("type") String type, Model model) {
		model.addAttribute("title", "General Feeds");
		model.addAttribute("feedItems", service.getAllFeedsByTopic(type));
		return TOPIC_LIST;
	}

	@RequestMapping("/media")
	public String getMediaFeed(@RequestParam("type") String type, Model model) {
		model.addAttribute("title", "Newsroom Feeds");
		model.addAttribute("feedItems", service.getAllFeedsByTopic(type));
		return TOPIC_LIST;
	}

	@RequestMapping("/investors")
	public String getInvestorsFeed(@RequestParam("type") String type, Model model) {
		model.addAttribute("title", "Investors Feed");
		model.addAttribute("feedItems", service.getAllFeedsByTopic(type));
		return TOPIC_LIST;
	}

	@RequestMapping("/blogs")
	public String getBlogsFeed(@RequestParam("type") String type, Model model) {
		model.addAttribute("title", "Blogs Feed");
		model.addAttribute("feedItems", service.getAllFeedsByTopic(type));
		return TOPIC_LIST;
	}
}
