package io.github.gunkim.multi.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

@Getter
@RequiredArgsConstructor
public enum Personnel {
    TWO(2),
    FOUR(4);

    @JsonValue
    private final int size;

    @JsonCreator
    public static Personnel fromString(String val) {
        return Stream.of(values()).collect(toMap(Objects::toString, e -> e)).get(val);
    }
}
