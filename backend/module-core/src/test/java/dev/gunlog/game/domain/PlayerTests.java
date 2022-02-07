package dev.gunlog.game.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PlayerTests {
    @Test
    @DisplayName("생성자 테스트")
    void init() {
        assertThatCode(() -> new Player("테스트 유저", null, false))
            .doesNotThrowAnyException();
    }
    @Test
    @DisplayName("게임 준비 테스트")
    void readyTest() {
        Player player = new Player("테스트 유저", null, false);
        player.ready();
        assertThat(player.isReady()).isTrue();
    }
    @Test
    @DisplayName("게임 준비 후 다시 준비 테스트")
    void readyToReadyTest() {
        Player player = new Player("테스트 유저", null, false);
        player.ready();
        player.ready();
        assertThat(player.isReady()).isFalse();
    }
}