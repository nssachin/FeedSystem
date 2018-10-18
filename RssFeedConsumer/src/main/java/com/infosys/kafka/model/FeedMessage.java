package com.infosys.kafka.model;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document (collection = "infy.feed")
public class FeedMessage {
	@Id
	public ObjectId id;
	public String topic;
	private String title;
    private String description;
    private String link;
    private String guid;
    private Date publishedDate;
}
