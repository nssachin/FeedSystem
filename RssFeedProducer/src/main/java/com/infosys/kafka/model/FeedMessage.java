package com.infosys.kafka.model;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FeedMessage {
	private String title;
    private String description;
    private String link;
    private String guid;
    private Date publishedDate;
}
