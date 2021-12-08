package dev.gunlog.multi.domain;

import dev.gunlog.multi.domain.enums.Mode;
import dev.gunlog.multi.domain.enums.Personnel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("게임 방 테스트")
public class GameRoomTests {
    @Test
    @DisplayName("값 세팅")
    void init() {
        assertThatThrownBy(() -> GameRoom.builder()
                .id(1l)
                .title("테스트")
                .players(List.of(new Player()))
                .gameMode(Mode.SPEED_ATTACK)
                .maxNumberOfPeople(Personnel.FOUR)
                .isStart(false)
                .build()).isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    @DisplayName("값 세팅 : 실패 -> 호스트 네임이 null")
    void init_is_name_null_fail() {
        assertThatThrownBy(() -> GameRoom.builder()
                .id(1l)
                .title("테스트")
                .players(List.of(new Player()))
                .gameMode(Mode.SPEED_ATTACK)
                .maxNumberOfPeople(Personnel.FOUR)
                .isStart(false)
                .build()).isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    @DisplayName("값 세팅 : 실패 -> 플레이어 정보가 없음")
    void init_is_player_empty_fail() {
        assertThatThrownBy(() -> GameRoom.builder()
                .id(1l)
                .title("테스트")
                .players(List.of())
                .gameMode(Mode.SPEED_ATTACK)
                .maxNumberOfPeople(Personnel.FOUR)
                .isStart(false)
                .build()).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("게임 시작")
    void start() {
        GameRoom room = GameRoom.builder()
                .id(1l)
                .title("테스트")
                .players(List.of(new Player("test")))
                .gameMode(Mode.SPEED_ATTACK)
                .maxNumberOfPeople(Personnel.FOUR)
                .isStart(false)
                .build();
        assertThat(room.start()).isEqualTo(room);
        assertThat(room.isStart()).isTrue();
        assertThat(room.getStartTime()).isNotNull();

        Player player = room.getPlayers().get(0);
        assertThat(player).isNotNull();
        assertThat(player.getGameInfo()).isNotNull();
    }
    @Test
    @DisplayName("게임 끝")
    void stop() {
        GameRoom room = GameRoom.builder()
                .id(1l)
                .title("테스트")
                .players(List.of(new Player("test")))
                .gameMode(Mode.SPEED_ATTACK)
                .maxNumberOfPeople(Personnel.FOUR)
                .isStart(true)
                .build();
        assertThat(room.stop()).isEqualTo(room);
        assertThat(room.isStart()).isFalse();
        assertThat(room.getStartTime()).isNull();

        Player player = room.getPlayers().get(0);
        assertThat(player).isNotNull();
        assertThat(player.getGameInfo()).isNull();
    }
    @Test
    @DisplayName("게임 방 입장")
    void addPlayer() {
        GameRoom room = GameRoom.builder()
                .id(1l)
                .title("테스트")
                .players(new ArrayList<>(Arrays.asList(new Player("test"))))
                .gameMode(Mode.SPEED_ATTACK)
                .maxNumberOfPeople(Personnel.FOUR)
                .isStart(false)
                .build();
        GameRoom returnedRoom = room.addPlayer("test2");
        assertThat(returnedRoom).isEqualTo(room);
        assertThat(room.getPlayers().size()).isEqualTo(2);
        assertThat(room.getPlayers().get(0).getNickname()).isEqualTo("test");
        assertThat(room.getPlayers().get(0).getGameInfo()).isNull();
    }
    @Test
    @DisplayName("게임 방 입장 : 이미 시작된 게임")
    void addPlayer_isStart_true() {
        GameRoom room = GameRoom.builder()
                .id(1l)
                .title("테스트")
                .players(new ArrayList<>(Arrays.asList(new Player("test"))))
                .gameMode(Mode.SPEED_ATTACK)
                .maxNumberOfPeople(Personnel.FOUR)
                .isStart(true)
                .build();
        GameRoom returnedRoom = room.addPlayer("test2");
        assertThat(returnedRoom).isEqualTo(room);
        assertThat(room.getPlayers().size()).isEqualTo(1);
    }
    @Test
    @DisplayName("게임 방 퇴장")
    void exitPlayer() {
        GameRoom room = GameRoom.builder()
                .id(1l)
                .title("테스트")
                .players(new ArrayList<>(Arrays.asList(new Player("test"), new Player("test2"))))
                .gameMode(Mode.SPEED_ATTACK)
                .maxNumberOfPeople(Personnel.FOUR)
                .isStart(false)
                .build();
        GameRoom returnedRoom = room.exitPlayer("test2");
        assertThat(returnedRoom).isEqualTo(room);
        assertThat(room.getPlayers().size()).isEqualTo(1);
    }
}