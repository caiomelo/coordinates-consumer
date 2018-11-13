package com.greenmile.consumer.configuration.kafka;

import com.greenmile.coordinate.producer.model.coordinate.VehicleCoordinate;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

/**
 *
 * @author caioalbmelo
 */
@Configuration
@EnableKafka
public class KafkaCoordinatesConsumerConfiguration {

    @Autowired
    @Value("${kafka.config.bootstrap.servers}")
    private String bootstrapAddresses;

    @Autowired
    @Value("${kafka.config.groupid.coordinate.consumers}")
    private String groupId;

    @Bean
    public ConsumerFactory<String, VehicleCoordinate> coordinateConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddresses);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(VehicleCoordinate.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, VehicleCoordinate> coordinateListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, VehicleCoordinate> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(coordinateConsumerFactory());
        return factory;
    }

}
