package com.infosys.feed.entity;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Feed {
	private String id;
	private String title;
	private String description;
	private String link;
	private String guid;
	private Date publishedDate;
	private String topic;
}
