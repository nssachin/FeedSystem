package com.infosys.kafka.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class Feed {
   
    private Map<String, List<FeedMessage>> entries = new HashMap<String, List<FeedMessage>>();
}
