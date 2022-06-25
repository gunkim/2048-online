package io.github.gunkim.multi.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomCreateResponseDto {
    private Long roomId;

    @Builder
    public RoomCreateResponseDto(Long roomId) {
        this.roomId = roomId;
    }
}