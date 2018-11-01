package com.infosys.kafka.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.infosys.kafka.model.FeedMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RibbonClient(name="consribbon")
public class ConsumerService {
	public String url = "http://FEEDAPI/rss/";
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	DiscoveryClient client;
	
	public void saveFeeds(List<FeedMessage> entites) {
		List<ServiceInstance> instances = client.getInstances("FEEDAPI");
		log.info("URL is {}", instances.get(0).getUri());
		restTemplate.postForObject(url+"feeds",entites, ResponseEntity.class);
	}
}
