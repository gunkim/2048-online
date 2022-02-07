package dev.gunlog.game.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

@DisplayName("게임판 테스트")
public class GameBoardTests {

    @Test
    @DisplayName("생성자 테스트")
    void init() {
        assertThatCode(() -> new GameBoard((board, row, col) -> 0, 3, 3))
            .doesNotThrowAnyException();
    }
    @Test
    @DisplayName("생성자에 가로 길이가 1 이하일 경우 실패한다")
    void initZeroRowSizeException() {
        assertThatThrownBy(() -> new GameBoard((board, row, col) -> 0, 0, 3))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("게임판의 가로 길이는 1 이상이어야 합니다.");
    }
    @Test
    @DisplayName("생성자에 세로 길이가 1 이하일 경우 실패한다")
    void initZeroColSizeException() {
        assertThatThrownBy(() -> new GameBoard((board, row, col) -> 0, 3, 0))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("게임판의 세로 길이는 1 이상이어야 합니다.");
    }

    @ParameterizedTest
    @EnumSource(MoveType.class)
    @DisplayName("이동 테스트")
    void move(MoveType type) {
        AtomicBoolean isInit = new AtomicBoolean(true);
        GameBoardGenerator generator = (board, row, col) -> {
            if (isInit.get()) {
                board[1][1] = 1;
                board[2][2] = 1;
                isInit.set(false);
                return 2;
            }
            ;
            return 0;
        };
        GameBoard board = new GameBoard(generator, 3, 3);
        boolean isMove = board.move(type);
        assertThat(isMove).isTrue();

        int[][] currentBoard = board.currentBoard();
        switch (type) {
            case TOP -> assertAll(
                () -> assertThat(currentBoard[0][1]).isEqualTo(1),
                () -> assertThat(currentBoard[0][2]).isEqualTo(1)
            );
            case BOTTOM -> assertAll(
                () -> assertThat(currentBoard[2][1]).isEqualTo(1),
                () -> assertThat(currentBoard[2][2]).isEqualTo(1)
            );
            case LEFT -> assertAll(
                () -> assertThat(currentBoard[1][0]).isEqualTo(1),
                () -> assertThat(currentBoard[2][0]).isEqualTo(1)
            );
            case RIGHT -> assertAll(
                () -> assertThat(currentBoard[1][2]).isEqualTo(1),
                () -> assertThat(currentBoard[2][2]).isEqualTo(1)
            );
        }
    }
}