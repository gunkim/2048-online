package dev.gunlog.multi.dto;

import dev.gunlog.multi.domain.GameRoom;
import dev.gunlog.multi.domain.Player;
import dev.gunlog.multi.domain.enums.Mode;
import dev.gunlog.multi.domain.enums.Personnel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class RoomCreateRequestDto {
    private String title;
    private Mode mode;
    private Personnel personnel;

    @Builder
    public RoomCreateRequestDto(String title, Mode mode, Personnel personnel) {
        this.title = title;
        this.mode = mode;
        this.personnel = personnel;
    }

    public GameRoom toEntity(String nickname) {
        return GameRoom.builder()
                .id(1l)
                .title(this.title)
                .isStart(false)
                .maxNumberOfPeople(this.personnel)
                .gameMode(this.mode)
                .players(new ArrayList<>(List.of(new Player(nickname))))
                .build();
    }
}