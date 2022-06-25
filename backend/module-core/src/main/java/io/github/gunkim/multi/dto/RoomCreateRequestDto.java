package io.github.gunkim.multi.dto;

import io.github.gunkim.multi.domain.GameRoom;
import io.github.gunkim.multi.domain.Player;
import io.github.gunkim.multi.domain.enums.Mode;
import io.github.gunkim.multi.domain.enums.Personnel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
                .players(new ArrayList<>(List.of(Player.builder().nickname(nickname).isReady(true).build())))
                .build();
    }
}