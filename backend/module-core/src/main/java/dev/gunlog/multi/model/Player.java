package dev.gunlog.multi.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class Player {
    private String nickname;
    @Setter
    private Game gameInfo;

    public Player(String nickname) {
        this(nickname, null);
    }
    @Builder
    public Player(String nickname, Game gameInfo) {
        this.nickname = nickname;
        this.gameInfo = gameInfo;
    }
    public Player(Player player) {
        this.nickname = player.getNickname();
        this.gameInfo = player.getGameInfo();
    }
}
