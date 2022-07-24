package com.demo.kafka.springbootkafka.streams;

import com.demo.kafka.springbootkafka.models.Balance;
import com.demo.kafka.springbootkafka.models.Customer;
import com.demo.kafka.springbootkafka.models.CustomerBalance;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.retrytopic.DestinationTopic;

import java.util.Properties;

@Configuration
public class ApacheStreamsConfiguration {


    public Topology buildTopology() {
        StreamsBuilder streamsBuilder = new StreamsBuilder();
        final String CUSTOMER_TOPIC = "customer";
        final String BALANCE_TOPIC = "balance";
        final String CUSTOMER_BALANCE_TOPIC = "customer-balance";

        KStream<String, Object> rockSongs = streamsBuilder.stream(BALANCE_TOPIC);
        KStream<String, Object> classicalSongs = streamsBuilder.stream(CUSTOMER_TOPIC);

        KStream<String, Object> allSongs = rockSongs.merge(classicalSongs);
        allSongs.to(CUSTOMER_BALANCE_TOPIC);
        return streamsBuilder.build();
    }
}
