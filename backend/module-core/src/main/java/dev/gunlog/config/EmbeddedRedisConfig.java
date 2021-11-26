package dev.gunlog.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

@Profile("dev")
@Slf4j
@Component
public class EmbeddedRedisConfig {
    @Value("${spring.redis.port}")
    private int redisPort;

    private RedisServer redisServer;

    @PostConstruct
    public void startRedis() throws IOException {
        this.redisServer = new RedisServer(redisPort);
        this.redisServer.start();
        log.info("임베디드 Redis 시작 성공");
    }
    @PreDestroy
    public void stopRedis() {
        redisServer.stop();
    }
}