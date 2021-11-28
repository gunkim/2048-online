package dev.gunlog.multi.domain;

import dev.gunlog.multi.model.Game;
import lombok.*;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayerRedis implements Serializable {
    @Indexed
    private String nickname;
    @Setter
    private GameRedis gameInfo;
    private boolean isReady;

    public PlayerRedis(String nickname) {
        this(nickname, null, false);
    }
    @Builder
    public PlayerRedis(String nickname, GameRedis gameInfo, boolean isReady) {
        this.nickname = nickname;
        this.gameInfo = gameInfo;
        this.isReady = isReady;
    }
    public PlayerRedis(PlayerRedis player) {
        this.nickname = player.getNickname();
        this.gameInfo = player.getGameInfo();
    }
    public void ready() {
        this.isReady = this.isReady ? false : true;
    }
}
