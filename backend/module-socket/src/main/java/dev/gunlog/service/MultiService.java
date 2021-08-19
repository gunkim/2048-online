package dev.gunlog.service;

import dev.gunlog.model.Game;
import dev.gunlog.model.Player;
import dev.gunlog.model.Room;
import dev.gunlog.repository.GameRoomRepository;
import dev.gunlog.repository.UserRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class MultiService {
    private final GameRoomRepository gameRoomRepository;
    private final UserRoomRepository userRoomRepository;

    public Room leftMove(String username) {
        return this.commonMove(username, game -> game.leftMove());
    }
    public Room rightMove(String username) {
        return this.commonMove(username, game -> game.rightMove());
    }
    public Room topMove(String username) {
        return this.commonMove(username, game -> game.topMove());
    }
    public Room bottomMove(String username) {
        return this.commonMove(username, game -> game.bottomMove());
    }
    public Room findRoomByRoomId(Integer roomId) {
        return gameRoomRepository.findRoomByRoomId(roomId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게임 방을 찾을 수 없습니다. ROOM_ID : "+roomId));
    }
    private Room commonMove(String username, Consumer<Game> gameConsumer) {
        Integer roomId = userRoomRepository.findRoomIdByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 들어가 있는 방을 찾을 수 없습니다. USERNAME : "+username));
        Room room = gameRoomRepository.findRoomByRoomId(roomId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게임 방을 찾을 수 없습니다. ROOM_ID : "+roomId));

        Stream<Player> players = room.getPlayers().stream();
        Player myPlayer = players
                .filter(player -> username.equals(player.getNickname()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("플레이어를 찾을 수 없습니다 : "+username));

        gameConsumer.accept(myPlayer.getGameInfo());
        return room;
    }
}