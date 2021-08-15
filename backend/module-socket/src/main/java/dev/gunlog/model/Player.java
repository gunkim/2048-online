package dev.gunlog.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Player {
    private String nickname;
    private Game gameInfo;

    public Player(String nickname) {
        this.nickname = nickname;
        this.gameInfo = new Game();
    }
}
