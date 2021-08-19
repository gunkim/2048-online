package dev.gunlog.multi.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Player {
    private String nickname;
    private Game gameInfo;

    public Player(String nickname) {
        this(nickname, new Game());
    }
    @Builder
    public Player(String nickname, Game gameInfo) {
        this.nickname = nickname;
        this.gameInfo = gameInfo;
    }
}
