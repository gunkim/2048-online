package dev.gunlog.multi.domain;

import lombok.*;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Player implements Serializable {
    @Indexed
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
