package dev.gunlog.api.room.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

@Getter
@RequiredArgsConstructor
public enum Mode {
    TIME_ATTACK("타임어택"),
    SPEED_ATTACK("스피드"),
    SURVIVAL("서바이벌");

    @JsonValue
    private final String title;

    @JsonCreator
    public static Mode fromString(String val) {
        return Stream.of(values()).collect(toMap(Objects::toString, e -> e)).get(val);
    }
}