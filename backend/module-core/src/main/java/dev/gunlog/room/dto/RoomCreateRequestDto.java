package dev.gunlog.room.dto;

import dev.gunlog.multi.domain.GameRoomRedis;
import dev.gunlog.room.domain.enums.Mode;
import dev.gunlog.room.domain.enums.Personnel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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

    public GameRoomRedis toEntity(String nickname) {
        return GameRoomRedis.builder()
                .id(1l)
                .title(this.title)
                .isStart(false)
                .maxNumberOfPeople(this.personnel)
                .gameMode(this.mode)
                .host(nickname)
                .build();
    }
}