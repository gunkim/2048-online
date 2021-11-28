package dev.gunlog.multi.service;

import dev.gunlog.multi.domain.GameRedis;
import dev.gunlog.multi.domain.GameRoomRedis;
import dev.gunlog.multi.domain.GameRoomRedisRepository;
import dev.gunlog.multi.domain.PlayerRedis;
import dev.gunlog.multi.model.Game;
import dev.gunlog.room.domain.enums.Mode;
import dev.gunlog.room.dto.RoomCreateRequestDto;
import dev.gunlog.room.dto.RoomListResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
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
    private final RedisTemplate<String, Game> redisTemplate;

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
        Optional<GameRoomRedis> test = roomRedisRepository.findByPlayersNickname(nickname);
        log.info(test.toString());
        return test
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
        GameRoomRedis room = requestDto.toEntity(nickname);
        room.addPlayer(nickname);
        return roomRedisRepository.save(room).getId();
    }
    public GameRoomRedis joinRoom(Long roomId, String nickname) {
        List<GameRoomRedis> list = roomRedisRepository.findAll();
        log.info(list.toString());
        Optional<GameRoomRedis> test = roomRedisRepository.findById(roomId);
        GameRoomRedis room = test
                .orElseThrow(() -> new IllegalArgumentException("게임 방을 찾을 수 없습니다. ROOM_ID : "+roomId));
        List<PlayerRedis> players = room.getPlayers();

        boolean isJoin = players.stream().anyMatch(player -> player.getNickname().equals(nickname));
        boolean isPeopleLimit = room.getMaxNumberOfPeople().getSize() == players.size();
        if(isJoin) {
            throw new IllegalArgumentException("해당 유저는 이미 방에 들어가 있습니다.");
        } else if(isPeopleLimit) {
            throw new IllegalArgumentException("해당 방은 이미 가득 찼습니다.");
        } else {
            room.addPlayer(nickname);
        }
        return room;
    }
    @Transactional
    public Long exitRoom(String nickname) {
        GameRoomRedis room = roomRedisRepository.findByPlayersNickname(nickname)
                .orElseThrow();
        room.exitPlayer(nickname);
        roomRedisRepository.save(room);

        if(room.getPlayers().size() == 0) {
            roomRedisRepository.deleteById(room.getId());
            return -1l;
        } else {
            return room.getId();
        }

    }
    public void gameStop(Long roomId) {
        GameRoomRedis room = roomRedisRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게임 방을 찾을 수 없습니다. ROOM_ID : "+roomId));
        room.gameStop();
        roomRedisRepository.save(room);
    }
    private GameRoomRedis commonMove(String nickname, Consumer<GameRedis> gameConsumer) {
        GameRoomRedis room = roomRedisRepository.findByPlayersNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 참여한 게임을 찾을 수 없습니다. NICKNAME : "+nickname));

        if(!room.isStart()) {
            return room;
        }
        LocalDateTime startDate = room.getStartTime();
        LocalDateTime endDate = LocalDateTime.now();

        List<PlayerRedis> players = room.getPlayers();

        PlayerRedis myPlayer = players.stream()
                .filter(player -> nickname.equals(player.getNickname()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("플레이어를 찾을 수 없습니다 : "+nickname));

        boolean isAllGameOver = players.stream().allMatch(player -> player.getGameInfo().isGameOver() == true);
        if(isAllGameOver) {
            room.gameStop();
            players.stream().forEach(player -> player.setGameInfo(null));
        }
        if(room.getGameMode() == Mode.TIME_ATTACK) {
            long gameInTime = ChronoUnit.MINUTES.between(startDate, endDate);
            if(gameInTime == 3l) {
                room.gameStop();
                players.stream().forEach(player -> player.setGameInfo(null));
            }
        } else if(room.getGameMode() == Mode.SPEED_ATTACK) {
            Arrays.stream(myPlayer.getGameInfo().getBoard()).forEach(row -> {
                Arrays.stream(row).forEach(col -> {
                    if(col == 11) {
                        room.gameStop();
                    }
                });
            });
        } else if(room.getGameMode() == Mode.SURVIVAL) {
            boolean isWinner = 1 == room.getPlayers().stream().filter(player -> !player.getGameInfo().isGameOver()).count();
            if(isWinner) {
                room.gameStop();
                players.stream().forEach(player -> player.setGameInfo(null));
            }
        }
        gameConsumer.accept(myPlayer.getGameInfo());
        roomRedisRepository.save(room);
        return room;
    }
    public GameRoomRedis ready(String nickname) {
        GameRoomRedis room = roomRedisRepository.findByPlayersNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 참여한 게임을 찾을 수 없습니다. NICKNAME : "+nickname));
        if(room.isStart()) {
            return null;
        }
        PlayerRedis me = room.getPlayers().stream().filter(player -> player.getNickname().equals(nickname)).findFirst().get();
        me.ready();
        roomRedisRepository.save(room);
        return room;
    }
}