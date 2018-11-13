package com.greenmile.consumer.configuration.kafka;

import com.greenmile.consumer.model.coordinates.VehicleCoordinates;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
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
    @Value("${kafka.config.groupid.coordinates.consumers}")
    private String groupId;

    @Bean
    public ConsumerFactory<String, VehicleCoordinates> coordinatesConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddresses);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.greenmile.producer.model.coordinate");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, VehicleCoordinates> coordinatesListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, VehicleCoordinates> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(coordinatesConsumerFactory());
        factory.setMessageConverter(new StringJsonMessageConverter());
        return factory;
    }

    @Bean
    public StringJsonMessageConverter jsonConverter() {
        return new StringJsonMessageConverter();
    }

}
