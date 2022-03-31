package com.layo.kafkaexample;

// import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.layo.kafkaexample.model.Greeting;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
// @ConditionalOnProperty(value = "example.kafka.consumer-enabled", havingValue = "true")
@Slf4j
public class GreetingsConsumer {

    // https://stackoverflow.com/questions/54426658/control-enabling-disabling-kafka-consumers-in-spring-boot
    @KafkaListener(topics = {"GREETING_DATA"}, autoStartup = "${example.kafka.consumer-enabled:false}")
    public void consume(final @Payload String message,
                        final @Header(KafkaHeaders.OFFSET) Integer offset,
                        final @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
                        final @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                        final @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                        final @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long ts,
                        final Acknowledgment acknowledgment) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement je = JsonParser.parseString(message);
        String msg = gson.toJson(je);
        log.info(String.format("#### -> Consumed message -> TIMESTAMP: %d\noffset: %d\nkey: %s\npartition: %d\ntopic: %s\n%s",
            ts, offset, key, partition, topic, msg));

        //log.info("Skipping ack");
        acknowledgment.acknowledge();
    }

    @KafkaListener(topics = "transaction-1", autoStartup = "${example.kafka.consumer-enabled:false}", 
        properties = {
            "key.deserializer: org.apache.kafka.common.serialization.StringDeserializer",
            "value.deserializer: org.springframework.kafka.support.serializer.JsonDeserializer"
    })
    public void listener(@Payload Greeting greeting,  ConsumerRecord<String, Greeting> cr) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        log.info("Topic [transaction-1] Received greeting {} ", gson.toJson(greeting));
        log.info(cr.toString());
    }
}

