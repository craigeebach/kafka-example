package com.layo.kafkaexample;

import com.layo.kafkaexample.model.Greeting;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class Producer {

    // Starting with Spring 4.3, if a class, which is configured as a Spring bean, has only one constructor, the @Autowired annotation can be omitted and Spring will use that constructor and inject all necessary dependencies.
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaTemplate<String, Object> kafkaJsonTemplate;

    public Producer(KafkaTemplate<String, String> kafkaTemplate, KafkaTemplate<String, Object> kafkaJsonTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaJsonTemplate = kafkaJsonTemplate;
    }

    // public ListenableFuture<SendResult<String, Object>> sendMessage(String topic, String key, String message) {
    //     KafkaTemplate<?, ?> kt = kafkaJsonTemplate;
    //     if (topic.equalsIgnoreCase("transaction-1")) {
    //         log.info("Sending Greeting message, topic={}, key={}, message={}", topic, key, message);
    //         return (ListenableFuture<SendResult<String, Object>>) this.kafkaJsonTemplate.send(topic, key, message);
    //     } else {
    //         log.info("Sending Greeting message, topic={}, key={}, message={}", topic, key, message);
    //         return (ListenableFuture<SendResult<String, Object>>) this.kafkaTemplate.send(topic, key, message);
    //     }
    // }

    public ListenableFuture<SendResult<String, Object>> sendJsonMessage(String topic, String key, Greeting message) {
        log.info("Sending Greeting message, topic={}, key={}, message={}", topic, key, message);
        return this.kafkaJsonTemplate.send(topic, key, message);
    }

    public ListenableFuture<SendResult<String, String>> sendStringMessage(String topic, String key, String message) {
        log.info("Sending Greeting message, topic={}, key={}, message={}", topic, key, message);
        return this.kafkaTemplate.send(topic, key, message);
    }
}
