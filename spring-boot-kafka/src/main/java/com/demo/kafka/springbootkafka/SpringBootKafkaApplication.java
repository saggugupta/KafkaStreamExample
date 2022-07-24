package com.demo.kafka.springbootkafka;

import com.demo.kafka.springbootkafka.consumer.CustomerConsumer;
import com.demo.kafka.springbootkafka.models.Balance;
import com.demo.kafka.springbootkafka.models.Customer;
import com.demo.kafka.springbootkafka.producer.BalanceProducer;
import com.demo.kafka.springbootkafka.producer.CustomerProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
@RequestMapping(value="/kafka")
public class SpringBootKafkaApplication {


	private final BalanceProducer balanceProducer;


	private final CustomerProducer customerProducer;

	@Autowired
	SpringBootKafkaApplication(BalanceProducer balanceProducer, CustomerProducer customerProducer) {
		this.balanceProducer = balanceProducer;
		this.customerProducer = customerProducer;
	}

	@PostMapping(value = "/publishbalance" , consumes = {"application/json"})
	public void sendBalanceMessageToKafkaTopic(@RequestBody  String balance) {
		this.balanceProducer.sendMessage(balance);
	}

	@PostMapping(value = "/publishcustomer" , consumes = {"application/json"})
	public void sendCustomerMessageToKafkaTopic(@RequestBody String customer) {
		this.customerProducer.sendMessage(customer);
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootKafkaApplication.class, args);
	}

}
