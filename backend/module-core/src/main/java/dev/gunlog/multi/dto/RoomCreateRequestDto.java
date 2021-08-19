package dev.gunlog.multi.dto;

import dev.gunlog.enums.Mode;
import dev.gunlog.enums.Personnel;
import dev.gunlog.multi.model.GameRoom;
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

    public GameRoom toModel() {
        return GameRoom.builder()
                .name(title)
                .maxNumberOfPeople(Personnel.FOUR)
                .gameMode(Mode.SPEED_ATTACK)
                .build();
    }
}