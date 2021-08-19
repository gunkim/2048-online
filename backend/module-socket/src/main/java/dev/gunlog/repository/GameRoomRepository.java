package dev.gunlog.repository;

import dev.gunlog.model.Room;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class GameRoomRepository {
    private static final Map<Integer, Room> roomInfoMap = new HashMap<>();

    public Optional<Room> findRoomByRoomId(Integer roomId) {
        return Optional.ofNullable(roomInfoMap.get(roomId));
    }
    public void save(Integer roomId, Room room) {
        roomInfoMap.put(roomId, room);
    }
    public void deleteByRoomId(Integer roomId) {
        roomInfoMap.remove(roomId);
    }
}