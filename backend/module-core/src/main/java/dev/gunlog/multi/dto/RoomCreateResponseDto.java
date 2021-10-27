package dev.gunlog.multi.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomCreateResponseDto {
    private Integer roomId;

    @Builder
    public RoomCreateResponseDto(Integer roomId) {
        this.roomId = roomId;
    }
}