package dev.gunlog.multi.domain;

import dev.gunlog.multi.model.GameRoom;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class GameRoomRepository {
    private static final Map<Integer, GameRoom> roomInfoMap = new HashMap<>();

    public Optional<GameRoom> findRoomByRoomId(Integer roomId) {
        return Optional.ofNullable(roomInfoMap.get(roomId));
    }
    public void save(Integer roomId, GameRoom room) {
        roomInfoMap.put(roomId, room);
    }
    public void deleteByRoomId(Integer roomId) {
        roomInfoMap.remove(roomId);
    }
}