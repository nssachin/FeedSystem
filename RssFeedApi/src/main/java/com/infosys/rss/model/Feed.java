package com.infosys.rss.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document (collection = "infy.rss")
public class Feed {
	private String title;
	private String description;
	private String link;
	@Id
	private String guid;
	private Date publishedDate;
	private String feedType;
}
