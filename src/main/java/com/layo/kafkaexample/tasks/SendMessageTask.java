package com.layo.kafkaexample.tasks;

import com.layo.kafkaexample.Producer;
import org.springframework.kafka.support.SendResult;
// import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.concurrent.ExecutionException;

// Not using this component, using "Greetings" UI driver instead
// @Component
@Slf4j
public class SendMessageTask {
    // private final Logger logger = LoggerFactory.getLogger(SendMessageTask.class);
    private final Producer producer;

    public SendMessageTask(Producer producer) {
        this.producer = producer;
    }

    // run every 3 sec
    // @Scheduled(fixedRateString = "5000")
    public void send() throws ExecutionException, InterruptedException {

        ListenableFuture<SendResult<String, String>> listenableFuture = this.producer.sendStringMessage("GREETING_DATA", "IN_KEY", LocalDate.now().toString());

        SendResult<String, String> result = listenableFuture.get();
        log.info(String.format("Produced:\ntopic: %s\noffset: %d\npartition: %d\nvalue size: %d", result.getRecordMetadata().topic(),
                result.getRecordMetadata().offset(),
                result.getRecordMetadata().partition(), result.getRecordMetadata().serializedValueSize()));
    }
}