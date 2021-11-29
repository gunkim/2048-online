package dev.gunlog.multi.service;

import dev.gunlog.multi.domain.GameRedis;
import dev.gunlog.multi.domain.GameRoomRedis;
import dev.gunlog.multi.domain.GameRoomRedisRepository;
import dev.gunlog.multi.domain.PlayerRedis;
import dev.gunlog.room.domain.enums.Mode;
import dev.gunlog.room.dto.RoomCreateRequestDto;
import dev.gunlog.room.dto.RoomListResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

@Slf4j
@RequiredArgsConstructor
@Service
public class MultiService {
    private final GameRoomRedisRepository roomRedisRepository;

    public GameRoomRedis leftMove(String username) {
        return this.commonMove(username, game -> game.leftMove());
    }
    public GameRoomRedis rightMove(String username) {
        return this.commonMove(username, game -> game.rightMove());
    }
    public GameRoomRedis topMove(String username) {
        return this.commonMove(username, game -> game.topMove());
    }
    public GameRoomRedis bottomMove(String username) {
        return this.commonMove(username, game -> game.bottomMove());
    }
    public GameRoomRedis findRoomByRoomId(Long roomId) {
        return roomRedisRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게임 방을 찾을 수 없습니다. ROOM_ID : "+roomId));
    }
    public GameRoomRedis findRoomByNickname(String nickname) {
        return roomRedisRepository.findByPlayersNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 참여한 게임을 찾을 수 없습니다 : "+nickname));
    }
    public List<RoomListResponseDto> getAllRooms() {
        return StreamSupport.stream(roomRedisRepository.findAll().spliterator(), false)
                .map(RoomListResponseDto::new)
                .collect(toList());
    }
    public GameRoomRedis gameStart(String nickname) {
        GameRoomRedis room = roomRedisRepository.findByPlayersNickname(nickname)
                .orElseThrow();
        boolean isHost = nickname.equals(room.getHost());
        boolean isNotGameStart = !room.isStart();
        boolean isAllReady = room.getPlayers().stream().allMatch(player -> player.isReady());

        if(isHost && isNotGameStart && isAllReady) {
            return roomRedisRepository.save(room.gameStart());
        }
        return null;
    }
    public Long createRoom(RoomCreateRequestDto requestDto, String nickname) {
        GameRoomRedis room = requestDto.toEntity(nickname).addPlayer(nickname);
        return roomRedisRepository.save(room).getId();
    }
    public GameRoomRedis joinRoom(Long roomId, String nickname) {
        GameRoomRedis room = this.findRoomByRoomId(roomId);

        List<PlayerRedis> players = room.getPlayers();
        boolean isJoin = players.stream().anyMatch(player -> player.getNickname().equals(nickname));
        boolean isPeopleLimit = room.getMaxNumberOfPeople().getSize() == players.size();
        if(isJoin) {
            throw new IllegalArgumentException("해당 유저는 이미 방에 들어가 있습니다.");
        } else if(isPeopleLimit) {
            throw new IllegalArgumentException("해당 방은 이미 가득 찼습니다.");
        }
        return room.addPlayer(nickname);
    }
    @Transactional
    public Long exitRoom(String nickname) {
        GameRoomRedis room = this.findRoomByNickname(nickname).exitPlayer(nickname);
        roomRedisRepository.save(room);

        boolean isZeroPlayer = room.getPlayers().size() == 0;
        if(isZeroPlayer) {
            roomRedisRepository.deleteById(room.getId());
            return -1l;
        } else {
            return room.getId();
        }

    }
    public void gameStop(Long roomId) {
        GameRoomRedis room = this.findRoomByRoomId(roomId).gameStop();
        roomRedisRepository.save(room);
    }
    private GameRoomRedis commonMove(String nickname, Consumer<GameRedis> gameConsumer) {
        GameRoomRedis room = this.findRoomByNickname(nickname);

        boolean isNotStart = !room.isStart();
        if(isNotStart) {
            return room;
        }
        PlayerRedis myPlayer = room.getPlayers().stream()
                .filter(player -> nickname.equals(player.getNickname()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("플레이어를 찾을 수 없습니다 : "+nickname));
        gameConsumer.accept(myPlayer.getGameInfo());

        return roomRedisRepository.save(room);
    }
    public GameRoomRedis ready(String nickname) {
        GameRoomRedis room = this.findRoomByNickname(nickname);
        if(room.isStart()) {
            return null;
        }
        PlayerRedis me = room.getPlayers().stream().filter(player -> player.getNickname().equals(nickname)).findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("플레이어가 참여한 게임을 찾을 수 없습니다. NICKNAME : "+nickname));
        me.ready();
        return roomRedisRepository.save(room);
    }
}