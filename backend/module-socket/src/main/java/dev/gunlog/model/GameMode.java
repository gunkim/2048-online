package dev.gunlog.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum GameMode {
    TIME_ATTACK("타임어택"),
    SPEED_ATTACK("스피드"),
    SURVIVAL("서바이벌");

    private String title;
}
