package com.demo.kafka.springbootkafka.producer;


import com.demo.kafka.springbootkafka.models.Balance;
import com.demo.kafka.springbootkafka.models.JsonSerde;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serdes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class BalanceProducer {

    private static final Logger logger = LoggerFactory.getLogger(BalanceProducer.class);
    private static final String TOPIC = "balance";

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(String balance) {
        logger.info(String.format("#### -> Producing balance message -> %s", balance));
        try {
            Balance balanceObj = new ObjectMapper().readValue(balance,Balance.class);
            this.kafkaTemplate.send(TOPIC, balanceObj.getAccountId() , balance);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
