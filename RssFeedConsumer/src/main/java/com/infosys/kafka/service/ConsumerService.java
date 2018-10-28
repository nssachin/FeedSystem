package com.infosys.kafka.service;

import java.awt.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RibbonClient(name="consribbon")
public class ConsumerService {
	public String url = "http://FEEDAPI/rss/";
	@Autowired
	RestTemplate restTemplate;
	
	public void saveFeeds() {
		restTemplate.getForObject(url+"feeds", List.class);
	}
}
