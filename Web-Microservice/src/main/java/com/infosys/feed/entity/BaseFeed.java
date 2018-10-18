package com.infosys.feed.entity;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class BaseFeed {
	
	private String title;
    private String description;
    private String link;
    private String guid;
    private Date publishedDate;

}
