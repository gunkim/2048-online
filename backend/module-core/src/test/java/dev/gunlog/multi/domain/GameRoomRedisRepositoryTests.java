package dev.gunlog.multi.domain;

import dev.gunlog.multi.model.Game;
import dev.gunlog.room.domain.enums.Mode;
import dev.gunlog.room.domain.enums.Personnel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@ActiveProfiles("dev")
@SpringBootTest
public class GameRoomRedisRepositoryTests {
    @Autowired
    private GameRoomRedisRepository repository;
    @Test
    public void test() {
        repository.deleteAll();
        PlayerRedis player = new PlayerRedis("gunkim");
        player.setGameInfo(new Game());
        long roomId = repository.save(GameRoomRedis.builder()
                .title("방 이름")
                .players(List.of(player))
                .host("gunkim")
                .isStart(false)
                .maxNumberOfPeople(Personnel.FOUR)
                .gameMode(Mode.SPEED_ATTACK)
                .build()).getId();

        System.out.println(repository.findById(roomId));
    }
}