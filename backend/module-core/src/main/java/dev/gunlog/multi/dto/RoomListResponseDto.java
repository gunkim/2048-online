package dev.gunlog.multi.dto;

import dev.gunlog.multi.domain.GameRoom;
import dev.gunlog.multi.domain.enums.Mode;
import dev.gunlog.multi.domain.enums.Personnel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RoomListResponseDto {
    private Long id;
    private String title;
    private String username;
    private Mode mode;
    private Personnel personnel;
    private int participant;

    public RoomListResponseDto(GameRoom room) {
        this.id = room.getId();
        this.title = room.getTitle();
        this.username = room.getHost();
        this.mode = room.getGameMode();
        this.personnel = room.getMaxNumberOfPeople();
        this.participant = room.getPlayers().size();
    }
    @Builder
    public RoomListResponseDto(Long id, String title, String username, Mode mode, Personnel personnel, int participant) {
        this.id = id;
        this.title = title;
        this.username = username;
        this.mode = mode;
        this.personnel = personnel;
        this.participant = participant;
    }
}