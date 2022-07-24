package com.demo.kafka.springbootkafka.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class BalanceConsumer {
    private final Logger logger = LoggerFactory.getLogger(BalanceConsumer.class);

    //@KafkaListener(topics = "balance", groupId = "group_id")
    public void consume(String message) throws IOException {
        logger.info(String.format("#### -> Consumed message -> %s", message));
    }
}
