package dev.gunlog.game.domain;

import dev.gunlog.multi.domain.Game;
import lombok.Builder;
import lombok.Getter;

public class Player {

    private String nickname;
    private Game board;
    @Getter
    private boolean isReady;

    @Builder
    public Player(String nickname, Game board, boolean isReady) {
        if(nickname == null || nickname.isBlank()) {
            throw new IllegalArgumentException("플레이어 이름은 꼭 필요합니다.");
        }
        this.nickname = nickname;
        this.board = board;
        this.isReady = isReady;
    }

    public static Player from(String nickname) {
        return Player.builder()
            .nickname(nickname)
            .build();
    }

    public void ready() {
        this.isReady = !this.isReady;
    }
}
