package dev.gunlog.room.dto;

import dev.gunlog.enums.Mode;
import dev.gunlog.enums.Personnel;
import dev.gunlog.room.domain.Room;
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

    public RoomListResponseDto(Room room) {
        this.id = room.getId();
        this.title = room.getTitle();
        this.username = room.getMember().getUsername();
        this.mode = room.getMode();
        this.personnel = room.getPersonnel();
    }
    @Builder
    public RoomListResponseDto(Long id, String title, String username, Mode mode, Personnel personnel) {
        this.id = id;
        this.title = title;
        this.username = username;
        this.mode = mode;
        this.personnel = personnel;
    }
}