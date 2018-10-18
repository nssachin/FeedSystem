package com.infosys.feed.entity;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document (collection = "#{feedService.getCollectionName()}")
public class Feed {
	@Id
	public ObjectId id;
	private String title;
	private String description;
	private String link;
	private String guid;
	private Date publishedDate;
	private String topic;
}