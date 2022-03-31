package com.layo.kafkaexample.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Greeting {

    private long id;
    private String content;
    private LocalDateTime timeSent;

    // needed for json deserialization by jackson
    public Greeting() {

    }

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }

}