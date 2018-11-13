package com.greenmile.info.provider.configuration;

import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import redis.embedded.RedisServer;

/**
 *
 * @author caioalbmelo
 */
@TestConfiguration
public class TestRedisConfiguration {

    @Value("${spring.redis.embedded.port}")
    private int embeddedRedisPort;

    @Value("${spring.redis.embedded.host}")
    private String embeddedRedisHost;

    private RedisServer embeddedRedisServer;

    @Bean
    public LettuceConnectionFactory connectionFactory() {
        LettuceConnectionFactory connFactory = new LettuceConnectionFactory(embeddedRedisHost, embeddedRedisPort);
        connFactory.afterPropertiesSet();
        return connFactory;
    }

    @PostConstruct
    public void startRedis() throws IOException {
        this.embeddedRedisServer = new RedisServer(embeddedRedisPort);
        this.embeddedRedisServer.start();
    }

    @PreDestroy
    public void stopRedis() {
        this.embeddedRedisServer.stop();
    }

}
