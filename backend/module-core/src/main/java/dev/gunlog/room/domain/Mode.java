package dev.gunlog.room.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Mode {
    TIME_ATTACK("타임어택"),
    SPEED_ATTACK("스피드"),
    SURVIVAL("서바이벌");

    @JsonValue
    private final String title;
}