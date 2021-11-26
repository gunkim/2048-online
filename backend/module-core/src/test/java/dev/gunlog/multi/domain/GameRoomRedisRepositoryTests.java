package dev.gunlog.multi.domain;

import dev.gunlog.room.domain.enums.Mode;
import dev.gunlog.room.domain.enums.Personnel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GameRoomRedisRepositoryTests {
    @Autowired
    private GameRoomRedisRepository repository;
    @Test
    public void test() {
        repository.deleteAll();
        repository.save(GameRoomRedis.builder()
                .id(1l)
                .title("방 이름")
                .host("gunkim")
                .isStart(false)
                .maxNumberOfPeople(Personnel.FOUR)
                .gameMode(Mode.SPEED_ATTACK)
                .build());
    }
}