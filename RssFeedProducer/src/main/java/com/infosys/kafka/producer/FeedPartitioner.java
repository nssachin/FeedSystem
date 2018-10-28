package com.infosys.kafka.producer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FeedPartitioner implements Partitioner {
	private static final Logger LOGGER = LoggerFactory.getLogger(FeedPartitioner.class);

	private static Map<String,Integer> feedToPartitionMap;

	@Override
	public void configure(Map<String, ?> configs) {
		LOGGER.info("Inside CountryPartitioner.configure {} ", configs);
		feedToPartitionMap = new HashMap<String, Integer>();
		for(Map.Entry<String,?> entry: configs.entrySet()){
			if(entry.getKey().startsWith("partitions.")){
				String keyName = entry.getKey();
				String value = (String)entry.getValue();
				LOGGER.info("Key - {}", keyName.substring(11));
				int paritionId = Integer.parseInt(keyName.substring(11));
				feedToPartitionMap.put(value,paritionId);
			}
		}
	}

	@Override
	public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
		// TODO Auto-generated method stub
		List partitions = cluster.availablePartitionsForTopic(topic);
		LOGGER.info("partitions - {}", partitions);
		LOGGER.info("KEY - {}", key.toString());
		String feedType = (String)key;
		if(feedToPartitionMap.containsKey(feedType)){
			//If the feed type is mapped to particular partition return it
			return feedToPartitionMap.get(feedType);
		}else {
			//If no feed is mapped to particular partition distribute between remaining partitions
			int noOfPartitions = cluster.topics().size();
			return  value.hashCode()%noOfPartitions + feedToPartitionMap.size() ;
		}
	}

	@Override
	public void close() {}

}
