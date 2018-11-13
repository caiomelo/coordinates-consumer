package com.greenmile.consumer.configuration.redis;

import com.greenmile.consumer.model.route.RouteInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 *
 * @author caioalbmelo
 */
@Configuration
public class RedisConfiguration {
    
    @Autowired
    private LettuceConnectionFactory connFactory;
    
    @Bean(name = "routeInfoRedisTemplate")
    public RedisTemplate<String, RouteInfo> notificationRedisTemplate() {
        RedisTemplate<String, RouteInfo> redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(connFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(RouteInfo.class));
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
    
}
