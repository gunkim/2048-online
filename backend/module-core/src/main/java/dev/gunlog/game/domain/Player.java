package dev.gunlog.game.domain;

import lombok.Builder;
import lombok.Getter;

public class Player {

    private String nickname;
    private GameBoard board;
    @Getter
    private boolean isReady;

    @Builder
    public Player(String nickname, GameBoard board, boolean isReady) {
        if (nickname == null || nickname.isBlank()) {
            throw new IllegalArgumentException("플레이어 이름은 꼭 필요합니다.");
        }
        this.nickname = nickname;
        this.board = board;
        this.isReady = isReady;
    }

    public void move(MoveType moveType) {
        this.board.move(moveType);
    }

    public static Player from(String nickname) {
        return Player.builder()
            .nickname(nickname)
            .build();
    }

    public void gameStart() {
        if (!isReady) {
            return;
        }
        board = new GameBoard(new Level1RandomGenerator(), 5, 5);
    }

    public void gameStop() {
        if (isReady) {
            return;
        }
        board = null;
    }

    public void ready() {
        this.isReady = !this.isReady;
    }
}
