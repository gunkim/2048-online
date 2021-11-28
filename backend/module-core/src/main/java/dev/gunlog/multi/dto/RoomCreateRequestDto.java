package dev.gunlog.multi.dto;

import dev.gunlog.multi.domain.GameRoomRedis;
import dev.gunlog.room.domain.enums.Mode;
import dev.gunlog.room.domain.enums.Personnel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class RoomCreateRequestDto implements Serializable {
    private String title;
    private String peoples;
    private String gameMode;

    public GameRoomRedis toModel() {
        return GameRoomRedis.builder()
                .title(title)
                .maxNumberOfPeople(Personnel.FOUR)
                .gameMode(Mode.SPEED_ATTACK)
                .build();
    }
}