package com.layo.kafkaexample;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class ProducerBajun {

	final KafkaProperties kafkaProperties;

	public ProducerBajun(KafkaProperties kafkaProperties) {
		this.kafkaProperties = kafkaProperties;
	}

	@Bean
	public Map<String, Object> producerConfiguration() {
		Map<String, Object> properties = new HashMap<>(kafkaProperties.buildProducerProperties());
		// We overwrite the default Kafka serializer. The manufacturer will serialize
		// the keys as
		// Strings using the kafka library StringSerializer and JSON values using
		// JsonSerializer.
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return properties;
	}

	@Bean
	ProducerFactory<String, Object> producerFactory() {
		return new DefaultKafkaProducerFactory<>(producerConfiguration());
	}

	@Bean
	// It is a template used to perform high-level operations. We are going to use
	// it to send messages to Kafka.
	public KafkaTemplate<String, Object> kafkaJsonTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}

	@Bean
	// It is a template used to perform high-level operations. We are going to use
	// it to send messages to Kafka.
	public KafkaTemplate<String, String> kafkaTemplate() {
		return new KafkaTemplate<String, String>(new DefaultKafkaProducerFactory<>(kafkaProperties.buildProducerProperties()));
	}

	// @Bean
	// public NewTopic topic() {
	// 	// The first parameter is responsible for the name of the new topic,
	// 	// the second for the number of partitions to be divided into,
	// 	// the third is the replication ratio. Leaving it at level , we will use one
	// 	// node.
	// 	return new NewTopic("transaction-1", 2, (short) 1);
	// }
}
