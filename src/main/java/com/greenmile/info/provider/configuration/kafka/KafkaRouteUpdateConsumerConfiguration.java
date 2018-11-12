package com.greenmile.info.provider.configuration.kafka;

import com.greenmile.info.provider.model.RouteUpdate;
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
public class KafkaRouteUpdateConsumerConfiguration {

    @Autowired
    @Value("${kafka.config.bootstrap.servers}")
    private String bootstrapAddresses;

    @Autowired
    @Value("${kafka.config.groupid.route.update}")
    private String routeUpdateGroupId;

    @Bean
    public ConsumerFactory<String, RouteUpdate> routeUpdateConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddresses);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, routeUpdateGroupId);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<RouteUpdate>());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, RouteUpdate> routeUpdateListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, RouteUpdate> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(routeUpdateConsumerFactory());
        return factory;
    }

}
