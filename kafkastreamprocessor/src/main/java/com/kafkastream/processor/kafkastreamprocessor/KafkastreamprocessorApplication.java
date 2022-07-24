package com.kafkastream.processor.kafkastreamprocessor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafkastream.processor.kafkastreamprocessor.models.Balance;
import com.kafkastream.processor.kafkastreamprocessor.models.Customer;
import com.kafkastream.processor.kafkastreamprocessor.models.CustomerBalance;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.processor.WallclockTimestampExtractor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.time.Duration;
import java.util.Map;
import java.util.function.Function;

import static org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.*;

@SpringBootApplication
@EnableKafka
@EnableKafkaStreams
public class KafkastreamprocessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkastreamprocessorApplication.class, args);
	}

	@Bean(name =
			KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
	public KafkaStreamsConfiguration kStreamsConfigs() {
		return new KafkaStreamsConfiguration(Map.of(
				APPLICATION_ID_CONFIG, "kafkastreamprocessor",
				BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
				DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName(),
				DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName(),
				DEFAULT_TIMESTAMP_EXTRACTOR_CLASS_CONFIG, WallclockTimestampExtractor.class.getName()
		));
	}

	@Bean
	public KStream<String, CustomerBalance> kStream (StreamsBuilder kStreamBuilder) {

		/*final JsonDeserializer<Customer> customerJsonDeserializer = new JsonDeserializer<>();
		customerJsonDeserializer.addTrustedPackages("com.kafkastream.processor.kafkastreamprocessor.models");
		final JsonDeserializer<Balance> balanceJsonDeserializer = new JsonDeserializer<>();
		balanceJsonDeserializer.addTrustedPackages("com.kafkastream.processor.kafkastreamprocessor.models.*","java.util","java.lang", "com.kafkastream.processor.kafkastreamprocessor.models");

		final JsonDeserializer<CustomerBalance> customerBalanceJsonDeserializer = new JsonDeserializer<>();
		customerBalanceJsonDeserializer.addTrustedPackages("com.kafkastream.processor.kafkastreamprocessor.models");
*/
	/*	Serde<Customer> customerSerde = Serdes.serdeFrom(new JsonSerializer<>(), customerJsonDeserializer);
		Serde<Balance> balanceSerde = Serdes.serdeFrom(new JsonSerializer<>(), balanceJsonDeserializer);

	*/
		final JsonDeserializer<CustomerBalance> customerBalanceJsonDeserializer = new JsonDeserializer<>();
		customerBalanceJsonDeserializer.addTrustedPackages("com.kafkastream.processor.kafkastreamprocessor.models");

		Serde<CustomerBalance> customerBalanceSerde = Serdes.serdeFrom(new JsonSerializer<>(), customerBalanceJsonDeserializer);

		KTable<String, String> customerTable = kStreamBuilder.table("customer", Consumed.with(Serdes.String(), Serdes.String()));
		KTable<String, String> balanceTable = kStreamBuilder.table("balance" , Consumed.with(Serdes.String(), Serdes.String()));



		KTable<String, CustomerBalance> joined = balanceTable.join(customerTable,
				(balance,customer ) ->{
					CustomerBalance cb = null;
					try {
						Balance balanceObj = new ObjectMapper().readValue(balance,Balance.class);
						Customer customerObj = new ObjectMapper().readValue(customer,Customer.class);
						if(balanceObj.getAccountId() == customerObj.getAccountId())
						{
							cb = CustomerBalance.builder().accountId(balanceObj.getAccountId()).customerId(customerObj.getCustomerId()).phone(customerObj.getPhone()).balance(balanceObj.getBalance()).build();
						}

					} catch (JsonProcessingException e) {
						throw new RuntimeException(e);
					}
					return cb;


				},
				Materialized.as("customer-balance")
		);


		joined.toStream().mapValues((value)->{
			System.out.println("value:- " + value);
			return value;
		}).to("customerbalance",Produced.with(Serdes.String(), customerBalanceSerde));

		return joined.toStream();
	}
}
