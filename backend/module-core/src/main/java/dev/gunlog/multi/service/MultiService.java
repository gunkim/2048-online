package dev.gunlog.multi.service;

import dev.gunlog.multi.domain.Game;
import dev.gunlog.multi.domain.GameRoom;
import dev.gunlog.multi.domain.GameRoomRepository;
import dev.gunlog.multi.domain.Player;
import dev.gunlog.room.dto.RoomCreateRequestDto;
import dev.gunlog.room.dto.RoomListResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

@Slf4j
@RequiredArgsConstructor
@Service
public class MultiService {
    private final GameRoomRepository roomRedisRepository;

    public GameRoom leftMove(String username) {
        return this.commonMove(username, game -> game.leftMove());
    }
    public GameRoom rightMove(String username) {
        return this.commonMove(username, game -> game.rightMove());
    }
    public GameRoom topMove(String username) {
        return this.commonMove(username, game -> game.topMove());
    }
    public GameRoom bottomMove(String username) {
        return this.commonMove(username, game -> game.bottomMove());
    }
    public GameRoom findRoomByRoomId(Long roomId) {
        return roomRedisRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게임 방을 찾을 수 없습니다. ROOM_ID : "+roomId));
    }
    public GameRoom findRoomByNickname(String nickname) {
        return roomRedisRepository.findByPlayersNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 참여한 게임을 찾을 수 없습니다 : "+nickname));
    }
    public List<RoomListResponseDto> getAllRooms() {
        return StreamSupport.stream(roomRedisRepository.findAll().spliterator(), false)
                .map(RoomListResponseDto::new)
                .collect(toList());
    }
    public GameRoom gameStart(String nickname) {
        GameRoom room = this.findRoomByNickname(nickname);
        boolean isHost = nickname.equals(room.getHost());
        boolean isNotGameStart = !room.isStart();
        boolean isAllReady = room.getPlayers().stream().allMatch(player -> player.isReady());

        if(isHost && isNotGameStart && isAllReady) {
            return roomRedisRepository.save(room.start());
        }
        return GameRoom.builder().id(-1l).build();
    }
    public Long createRoom(RoomCreateRequestDto requestDto, String nickname) {
        GameRoom room = requestDto.toEntity(nickname).addPlayer(nickname);
        return roomRedisRepository.save(room).getId();
    }
    public GameRoom joinRoom(Long roomId, String nickname) {
        GameRoom room = this.findRoomByRoomId(roomId);

        List<Player> players = room.getPlayers();
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
    public GameRoom exitRoom(String nickname) {
        GameRoom room;
        try {
            room = this.findRoomByNickname(nickname).exitPlayer(nickname);
        } catch(IllegalArgumentException e) {
            return GameRoom.builder().id(-1l).build();
        }
        roomRedisRepository.save(room);

        boolean isZeroPlayer = room.getPlayers().size() == 0;
        if(isZeroPlayer) {
            roomRedisRepository.deleteById(room.getId());
            return GameRoom.builder().id(-1l).build();
        } else {
            return room;
        }
    }
    public void gameStop(Long roomId) {
        GameRoom room = this.findRoomByRoomId(roomId).stop();
        roomRedisRepository.save(room);
    }
    private GameRoom commonMove(String nickname, Consumer<Game> gameConsumer) {
        GameRoom room = this.findRoomByNickname(nickname);

        boolean isNotStart = !room.isStart();
        if(isNotStart) {
            return room;
        }
        Player myPlayer = room.getPlayers().stream()
                .filter(player -> nickname.equals(player.getNickname()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("플레이어를 찾을 수 없습니다 : "+nickname));
        gameConsumer.accept(myPlayer.getGameInfo());

        return roomRedisRepository.save(room);
    }
    public GameRoom ready(String nickname) {
        GameRoom room = this.findRoomByNickname(nickname);
        if(room.isStart()) {
            return null;
        }
        Player me = room.getPlayers().stream().filter(player -> player.getNickname().equals(nickname)).findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("플레이어가 참여한 게임을 찾을 수 없습니다. NICKNAME : "+nickname));
        me.ready();
        return roomRedisRepository.save(room);
    }
}