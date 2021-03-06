package com.infosys.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.infosys.kafka.model.Feed;
import com.infosys.kafka.model.FeedMessage;
import com.infosys.kafka.producer.RssFeedProducer;
import com.infosys.kafka.producer.RssFeedReader;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

	@Bean
	public Map<String, Object> producerConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		/*props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, FeedPartitioner.class.getCanonicalName());
		props.put("partition.0", "infosys.info");
		props.put("partition.1", "infosys.media");
		props.put("partition.2", "infosys.blog");
		props.put("partition.3", "infosys.investors");*/
		props.put(ProducerConfig.ACKS_CONFIG, "all");
		return props;
	}

	@Bean
	public ProducerFactory<String, FeedMessage> producerFactory() {
		return new DefaultKafkaProducerFactory<>(producerConfigs());
	}

	@Bean
	public KafkaTemplate<String, FeedMessage> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}

	@Bean
	public RssFeedReader reader() {
		return new RssFeedReader();
	}

	@Bean
	public RssFeedProducer writer() {;
	return new RssFeedProducer();
	}

	@Bean
	public Job importUserJob(Step step1) {
		return jobBuilderFactory.get("importUserJob")
				.incrementer(new RunIdIncrementer())
				.flow(step1)
				.end()
				.build();
	}

	@Bean
	public Step step1(ItemReader<Feed> reader,
			ItemWriter<Feed> writer) {
		return stepBuilderFactory.get("step1")
				.<Feed, Feed> chunk(1)
				.reader(reader)
				.writer(writer)
				.build();
	}
}