package dev.gunlog;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootApplication
public class RedisTest {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    void test() {
        ValueOperations<String, Object> vop = redisTemplate.opsForValue();
        vop.set("test", "jdk");

        String result = (String) vop.get("test");
        System.out.println("========");
        System.out.println(result);
    }
}
