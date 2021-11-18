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
    private boolean isReady;

    public Player(String nickname) {
        this(nickname, null, false);
    }
    @Builder
    public Player(String nickname, Game gameInfo, boolean isReady) {
        this.nickname = nickname;
        this.gameInfo = gameInfo;
        this.isReady = isReady;
    }
    public Player(Player player) {
        this.nickname = player.getNickname();
        this.gameInfo = player.getGameInfo();
    }
    public void ready() {
        this.isReady = this.isReady ? false : true;
    }
}
