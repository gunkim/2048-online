package io.github.gunkim.multi.service;

import io.github.gunkim.multi.domain.Game;
import io.github.gunkim.multi.domain.GameRoom;
import io.github.gunkim.multi.domain.GameRoomRepository;
import io.github.gunkim.multi.domain.Player;
import io.github.gunkim.multi.domain.enums.Mode;
import io.github.gunkim.multi.domain.enums.Personnel;
import io.github.gunkim.multi.dto.RoomCreateRequestDto;
import io.github.gunkim.multi.dto.RoomListResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@DisplayName("멀티 서비스 테스트")
@ExtendWith(SpringExtension.class)
public class MultiServiceTests {
    @InjectMocks
    private MultiService multiService;
    @Mock
    private GameRoomRepository gameRoomRepository;

    @Test
    @DisplayName("왼쪽 이동")
    void leftMove() {
        given(gameRoomRepository.findByPlayersNickname(anyString()))
                .willReturn(Optional.of(GameRoom.builder()
                        .isStart(true)
                        .players(List.of(Player.builder().nickname("test").gameInfo(new Game()).build()))
                        .build()));
        multiService.leftMove("test");
    }

    @Test
    @DisplayName("오른쪽 이동")
    void rightMove() {
        given(gameRoomRepository.findByPlayersNickname(anyString()))
                .willReturn(Optional.of(GameRoom.builder()
                        .isStart(true)
                        .players(List.of(Player.builder().nickname("test").gameInfo(new Game()).build()))
                        .build()));
        multiService.rightMove("test");
    }

    @Test
    @DisplayName("위쪽 이동")
    void topMove() {
        given(gameRoomRepository.findByPlayersNickname(anyString()))
                .willReturn(Optional.of(GameRoom.builder()
                        .isStart(true)
                        .players(List.of(Player.builder().nickname("test").gameInfo(new Game()).build()))
                        .build()));
        multiService.topMove("test");
    }

    @Test
    @DisplayName("아래쪽 이동")
    void bottomMove() {
        given(gameRoomRepository.findByPlayersNickname(anyString()))
                .willReturn(Optional.of(GameRoom.builder()
                        .isStart(true)
                        .players(List.of(Player.builder().nickname("test").gameInfo(new Game()).build()))
                        .build()));
        multiService.bottomMove("test");
    }

    @Test
    @DisplayName("방 번호로 게임 정보 조회")
    void findRoomByRoomId() {
        given(gameRoomRepository.findById(anyLong()))
                .willReturn(Optional.of(GameRoom.builder().players(List.of(new Player("test"))).build()));
        GameRoom gameRoom = multiService.findRoomByRoomId(1l);
        assertThat(gameRoom).isNotNull();
    }

    @Test
    @DisplayName("참가자 이름으로 참여한 게임 정보 조회")
    void findRoomByNickname() {
        given(gameRoomRepository.findByPlayersNickname(anyString()))
                .willReturn(Optional.of(GameRoom.builder().players(List.of(new Player("test"))).build()));
        GameRoom gameRoom = multiService.findRoomByNickname("test");
        assertThat(gameRoom).isNotNull();
    }

    @Test
    @DisplayName("모든 방 목록 조회")
    void getAllRooms() {
        given(gameRoomRepository.findAll())
                .willReturn(List.of(GameRoom.builder()
                        .id(1l)
                        .title("test room")
                        .gameMode(Mode.SPEED_ATTACK)
                        .maxNumberOfPeople(Personnel.FOUR)
                        .players(List.of(new Player("test")))
                        .build()));
        List<RoomListResponseDto> rooms = multiService.getAllRooms();
        assertThat(rooms.size()).isEqualTo(1);
        RoomListResponseDto room = rooms.get(0);
        assertThat(room.getId()).isEqualTo(1l);
        assertThat(room.getTitle()).isEqualTo("test room");
        assertThat(room.getMode()).isEqualTo(Mode.SPEED_ATTACK);
        assertThat(room.getPersonnel()).isEqualTo(Personnel.FOUR);
        assertThat(room.getParticipant()).isEqualTo(1);
        assertThat(room.getUsername()).isEqualTo("test");
    }

    @Test
    @DisplayName("게임 시작")
    void gameStart() {
        given(gameRoomRepository.findByPlayersNickname(anyString()))
                .willReturn(Optional.of(GameRoom.builder()
                        .isStart(false)
                        .players(List.of(Player.builder()
                                .nickname("test")
                                .isReady(true)
                                .build())
                        )
                        .build()));
        multiService.gameStart("test");
        verify(gameRoomRepository).save(any(GameRoom.class));
    }

    @Test
    @DisplayName("게임 시작 : 모든 플레이어가 준비하지 않았을 때")
    void gameStart_allPlayer_notReady() {
        given(gameRoomRepository.findByPlayersNickname(anyString()))
                .willReturn(Optional.of(GameRoom.builder()
                        .isStart(false)
                        .players(List.of(Player.builder()
                                .nickname("test")
                                .isReady(false)
                                .build())
                        )
                        .build()));
        GameRoom room = multiService.gameStart("test");
        assertThat(room.getId()).isEqualTo(-1l);
    }

    @Test
    @DisplayName("게임 시작 : 방장이 아니면 시작할 수 없음")
    void gameStart_isPlayer_notHost() {
        given(gameRoomRepository.findByPlayersNickname(anyString()))
                .willReturn(Optional.of(GameRoom.builder()
                        .isStart(false)
                        .players(List.of(Player.builder()
                                .nickname("test")
                                .isReady(true)
                                .build())
                        )
                        .build()));
        GameRoom room = multiService.gameStart("test1");
        assertThat(room.getId()).isEqualTo(-1l);
    }

    @Test
    @DisplayName("게임 시작 : 이미 시작된 게임")
    void gameStart_already_started() {
        given(gameRoomRepository.findByPlayersNickname(anyString()))
                .willReturn(Optional.of(GameRoom.builder()
                        .isStart(true)
                        .players(List.of(Player.builder()
                                .nickname("test")
                                .isReady(true)
                                .build())
                        )
                        .build()));
        GameRoom room = multiService.gameStart("test");
        assertThat(room.getId()).isEqualTo(-1l);
    }

    @Test
    @DisplayName("방 생성")
    void createRoom() {
        given(gameRoomRepository.save(any(GameRoom.class))).willReturn(GameRoom.builder().id(1l).players(List.of(Player.builder().nickname("test").build())).build());

        RoomCreateRequestDto requestDto = RoomCreateRequestDto.builder()
                .title("test room")
                .mode(Mode.SPEED_ATTACK)
                .personnel(Personnel.FOUR)
                .build();
        Long id = multiService.createRoom(requestDto, "test");
        assertThat(id).isNotNull().isEqualTo(1l);
        verify(gameRoomRepository).save(any(GameRoom.class));
    }

    @Test
    @DisplayName("방 입장")
    void joinRoom() {
        given(gameRoomRepository.findById(anyLong()))
                .willReturn(Optional.of(GameRoom.builder().id(1l).maxNumberOfPeople(Personnel.FOUR).players(new ArrayList<>(List.of(new Player("test")))).build()));
        GameRoom room = multiService.joinRoom(1l, "test1");
        assertThat(room).isNotNull();
        assertThat(room.getPlayers().size()).isEqualTo(2);
        assertThat(room.getPlayers().get(1).getNickname()).isEqualTo("test1");
    }

    @Test
    @DisplayName("방 입장 : 이미 들어가 있을 때")
    void joinRoom_already_join() {
        given(gameRoomRepository.findById(anyLong()))
                .willReturn(Optional.of(GameRoom.builder().id(1l).maxNumberOfPeople(Personnel.FOUR).players(new ArrayList<>(List.of(new Player("test")))).build()));
        assertThatThrownBy(() -> multiService.joinRoom(1l, "test")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("방 입장 : 인원 수 초과")
    void joinRoom_full() {
        given(gameRoomRepository.findById(anyLong()))
                .willReturn(Optional.of(GameRoom.builder()
                        .id(1l)
                        .maxNumberOfPeople(Personnel.TWO)
                        .players(new ArrayList<>(List.of(new Player("test1"), new Player("test2"))))
                        .build()));
        assertThatThrownBy(() -> multiService.joinRoom(1l, "test3")).isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    @DisplayName("방 퇴장")
    void exitRoom() {
        given(gameRoomRepository.findByPlayersNickname(anyString()))
                .willReturn(Optional.of(GameRoom.builder()
                        .id(1l)
                        .isStart(true)
                        .players(List.of(
                                Player.builder().nickname("test").gameInfo(new Game()).build(),
                                Player.builder().nickname("test1").gameInfo(new Game()).build()))
                        .build()));
        GameRoom room = multiService.exitRoom("test1");
        assertThat(room).isNotNull();
        assertThat(room.getId()).isEqualTo(1l);
        verify(gameRoomRepository).save(any(GameRoom.class));
    }
    @Test
    @DisplayName("방 퇴장 : 인원 수가 0이 될 경우 방 삭제")
    void exitRoom_drop_room() {
        given(gameRoomRepository.findByPlayersNickname(anyString()))
                .willReturn(Optional.of(GameRoom.builder()
                        .id(1l)
                        .isStart(true)
                        .players(List.of(Player.builder().nickname("test").build()))
                        .build()));
        GameRoom room = multiService.exitRoom("test");
        assertThat(room).isNotNull();
        assertThat(room.getId()).isEqualTo(-1l);
        verify(gameRoomRepository).deleteById(1l);
        verify(gameRoomRepository).save(any(GameRoom.class));
    }
    @Test
    @DisplayName("게임 정지")
    void gameStop() {
        given(gameRoomRepository.findById(anyLong()))
                .willReturn(Optional.of(GameRoom.builder()
                        .id(1l)
                        .maxNumberOfPeople(Personnel.TWO)
                        .players(new ArrayList<>(List.of(new Player("test1"))))
                        .build()));
        multiService.gameStop(1l);
        verify(gameRoomRepository).save(any(GameRoom.class));
    }
    @Test
    @DisplayName("게임 준비")
    void ready() {
        given(gameRoomRepository.findByPlayersNickname(anyString()))
                .willReturn(Optional.of(GameRoom.builder()
                        .id(1l)
                        .isStart(false)
                        .players(List.of(Player.builder().nickname("test").build()))
                        .build()));
        given(gameRoomRepository.save(any()))
                .willReturn(GameRoom.builder()
                        .id(1l)
                        .isStart(true)
                        .players(List.of(Player.builder().nickname("test").isReady(true).build()))
                        .build());
        GameRoom room = multiService.ready("test");
        assertThat(room).isNotNull();
        assertThat(room.getPlayers().get(0).isReady()).isTrue();
        verify(gameRoomRepository).save(any(GameRoom.class));
    }
    @Test
    @DisplayName("게임 준비 : 게임이 이미 시작되었을 때")
    void ready_isStart_true() {
        given(gameRoomRepository.findByPlayersNickname(anyString()))
                .willReturn(Optional.of(GameRoom.builder()
                        .id(1l)
                        .isStart(true)
                        .players(List.of(Player.builder().nickname("test").build()))
                        .build()));
        given(gameRoomRepository.save(any()))
                .willReturn(GameRoom.builder()
                        .id(1l)
                        .isStart(true)
                        .players(List.of(Player.builder().nickname("test").build()))
                        .build());
        GameRoom room = multiService.ready("test");
        assertThat(room).isNull();
    }
}