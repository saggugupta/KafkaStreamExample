package com.demo.kafka.springbootkafka.producer;

import com.demo.kafka.springbootkafka.models.Balance;
import com.demo.kafka.springbootkafka.models.Customer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CustomerProducer {
    private static final Logger logger = LoggerFactory.getLogger(CustomerProducer.class);
    private static final String TOPIC = "customer";

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(String customer) {
        logger.info(String.format("#### -> Producing Csutomer message -> %s", customer));
        try {
            Customer balanceObj = new ObjectMapper().readValue(customer,Customer.class);
            this.kafkaTemplate.send(TOPIC, balanceObj.getAccountId() , customer);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        //this.kafkaTemplate.send(TOPIC, customer.getAccountId(), customer);
    }
}
