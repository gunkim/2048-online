package dev.gunlog.api.room.dto;

import dev.gunlog.enums.Mode;
import dev.gunlog.enums.Personnel;
import dev.gunlog.api.member.domain.Member;
import dev.gunlog.api.room.domain.Room;
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

    public Room toEntity(Member member) {
        return Room.builder()
                .title(this.title)
                .mode(this.mode)
                .personnel(this.personnel)
                .member(member)
                .build();
    }
}