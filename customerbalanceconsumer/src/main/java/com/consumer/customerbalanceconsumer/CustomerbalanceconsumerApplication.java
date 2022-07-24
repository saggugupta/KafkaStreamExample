package com.consumer.customerbalanceconsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

import java.io.IOException;

@SpringBootApplication
public class CustomerbalanceconsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerbalanceconsumerApplication.class, args);
	}


	private final Logger logger = LoggerFactory.getLogger(CustomerbalanceconsumerApplication.class);

	@KafkaListener(topics = "customerbalance", groupId = "group_id")
	public void consume(String message) throws IOException {
		logger.info(String.format("#### -> Consumed message -> %s", message));
	}
}
