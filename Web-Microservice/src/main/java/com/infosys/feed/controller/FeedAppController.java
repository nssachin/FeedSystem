package com.infosys.feed.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.infosys.feed.configuration.AppProperties;
import com.infosys.feed.service.FeedService;

@Controller
public class FeedAppController {
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
		model.addAttribute("topics", properties.getTopics());
		return "index";
	}

	@RequestMapping("/info")
	public String getGenralInfo(Model model) {
		model.addAttribute("feedItems", service.getAllFeedsByTopic("infosys.info"));
		return "topic-list";
	}
}
