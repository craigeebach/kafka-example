package com.layo.kafkaexample;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

// @org.springframework.context.annotation.Configuration
public class ConsumerBajun {

	// final KafkaProperties kafkaProperties;

	// public ConsumerBajun(KafkaProperties kafkaProperties) {
	// 	this.kafkaProperties = kafkaProperties;
	// }

	// @Bean
	// ConsumerFactory<String, Object> consumerFactory() {
	// 	final JsonDeserializer<Object> jsonDeserializer = new JsonDeserializer<>();
	// 	// The addTrustedPackages functionality is responsible for determining trusted packages. For our needs, we will not define them, so ‘*’ means that we trust all packages.
	// 	jsonDeserializer.addTrustedPackages("*");
	// 	return new DefaultKafkaConsumerFactory<>(kafkaProperties.buildConsumerProperties(), new StringDeserializer(),
	// 			jsonDeserializer);
	// }

	// @Bean
	// ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
	// 	ConcurrentKafkaListenerContainerFactory<String, Object> concurrentKafkaListenerContainerFactory = new ConcurrentKafkaListenerContainerFactory();
	// 	concurrentKafkaListenerContainerFactory.setConsumerFactory(consumerFactory());

	// 	return concurrentKafkaListenerContainerFactory;
	// }

}
