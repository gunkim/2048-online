package io.github.gunkim.multi.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("게임 테스트")
public class GameTests {
    @Test
    @DisplayName("기본 세팅")
    void boardDefaultInit() {
        Game game = new Game();

        int sum = Arrays.stream(game.getBoard()).mapToInt(i -> Arrays.stream(i).sum()).sum();
        assertThat(sum >= 2).isTrue();
    }
    @Test
    @DisplayName("왼쪽 이동")
    void leftMove() {
        Game game = new Game(new Row[] {
                new Row(new int[]{0,0,0,0}),
                new Row(new int[]{0,2,2,0}),
                new Row(new int[]{0,0,2,0}),
                new Row(new int[]{0,0,0,0})
        }, 4);
        game.leftMove();
        assertThat(game.getBoard()[1][0]).isEqualTo(3);
        assertThat(game.getBoard()[2][0]).isEqualTo(2);
    }
    @Test
    @DisplayName("오른쪽 이동")
    void rightMove() {
        Game game = new Game(new Row[] {
                new Row(new int[]{0,0,0,0}),
                new Row(new int[]{0,2,2,0}),
                new Row(new int[]{0,0,2,0}),
                new Row(new int[]{0,0,0,0})
        }, 4);
        game.rightMove();
        assertThat(game.getBoard()[1][3]).isEqualTo(3);
        assertThat(game.getBoard()[2][3]).isEqualTo(2);
    }
    @Test
    @DisplayName("위쪽 이동")
    void topMove() {
        Game game = new Game(new Row[] {
                new Row(new int[]{0,0,0,0}),
                new Row(new int[]{0,2,2,0}),
                new Row(new int[]{0,0,2,0}),
                new Row(new int[]{0,0,0,0})
        }, 4);
        game.topMove();
        assertThat(game.getBoard()[0][1]).isEqualTo(2);
        assertThat(game.getBoard()[0][2]).isEqualTo(3);
    }
    @Test
    @DisplayName("아래쪽 이동")
    void bottomMove() {
        Game game = new Game(new Row[] {
                new Row(new int[]{0,0,0,0}),
                new Row(new int[]{0,2,2,0}),
                new Row(new int[]{0,0,2,0}),
                new Row(new int[]{0,0,0,0})
        }, 4);
        game.bottomMove();
        assertThat(game.getBoard()[3][1]).isEqualTo(2);
        assertThat(game.getBoard()[3][2]).isEqualTo(3);
    }
}
