package com.layo.kafkaexample.model;

import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;

@Data
public class GreetingForm {

    private String topic;
    private List<String> topics = Lists.newArrayList("GREETING_DATA", "transaction-1");

}