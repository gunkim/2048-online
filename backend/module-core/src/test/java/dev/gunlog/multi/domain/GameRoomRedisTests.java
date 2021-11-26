package dev.gunlog.multi.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GameRoomRedisTests {
    @Autowired
    private MemberInfoRedisRepository repository;
    @Test
    public void test() {
        MemberInfoRedis memberInfo = MemberInfoRedis.builder()
                .nickname("gunkim")
                .roomId(1l)
                .build();

        repository.save(memberInfo);
    }
}