package com.layo.kafkaexample.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Account {

    private String holder;
    private String funds;

}