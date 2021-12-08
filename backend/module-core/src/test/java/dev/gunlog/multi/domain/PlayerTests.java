package dev.gunlog.multi.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("플레이어 테스트")
public class PlayerTests {
    @Test
    @DisplayName("기본 세팅")
    void init() {
        Player player = new Player("test");
        assertThat(player.getNickname()).isEqualTo("test");
        assertThat(player.isReady()).isFalse();
    }
    @Test
    @DisplayName("준비")
    void ready() {
        Player player = new Player("test");
        player.ready();
        assertThat(player.isReady()).isTrue();
    }
}