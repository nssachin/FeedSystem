package com.infosys.kafka.producer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

import com.infosys.kafka.model.Feed;
import com.infosys.kafka.model.FeedMessage;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

@Component
public class RssFeedReader implements ItemReader<Feed> {
	private static final Logger LOGGER = LoggerFactory.getLogger(RssFeedReader.class);

	private boolean batchJobState  = false;

	@Override
	public Feed read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if(! batchJobState) {
			ClassLoader classLoader = getClass().getClassLoader();
			InputStream fstream = classLoader.getResourceAsStream("feed.txt");
			Feed rssFeed = new Feed();
			try (BufferedReader br = new BufferedReader(new InputStreamReader(fstream))) {
				String line;
				List<FeedMessage> messages ;
				while ((line = br.readLine()) != null) {
					messages =  new ArrayList<FeedMessage>();
					String[] value = line.split("=");
					SyndFeed feed = readFeed(value[1]);
					List<SyndEntry> entries = feed.getEntries();
					for (SyndEntry entry : entries) {
						messages.add(createFeedMessage(entry));
					}
					rssFeed.getEntries().put(value[0], messages);
				}
			}
			batchJobState = true;

			LOGGER.info("Total Size: {} " , rssFeed.getEntries().size());
			return rssFeed;
		}
		return null;
	}

	private static SyndFeed readFeed(String spec) throws IOException, FeedException {
		URL feedSource = new URL(spec);
		SyndFeedInput input = new SyndFeedInput();
		return input.build(new XmlReader(feedSource));
	}

	private FeedMessage createFeedMessage(SyndEntry entry) {
		FeedMessage message = new FeedMessage();
		message.setDescription(entry.getDescription().getValue());
		message.setLink(entry.getLink());
		message.setTitle(entry.getTitle());
		message.setPublishedDate(entry.getPublishedDate());
		if (null == entry.getUri() || entry.getUri().isEmpty()) {
			message.setGuid(entry.getLink());
		} else {
			message.setGuid(entry.getUri()) ;	
		}
		return message;
	}
}
