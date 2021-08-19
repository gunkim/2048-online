package dev.gunlog.repository;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRoomRepository {
    private static final Map<String, Integer> userRoomInfoMap = new HashMap<>();

    public Optional<Integer> findRoomIdByUsername(String username) {
        return Optional.ofNullable(userRoomInfoMap.get(username));
    }
    public void save(String username, Integer roomId) {
        userRoomInfoMap.put(username, roomId);
    }

    public void deleteByUsername(String username) {
        userRoomInfoMap.remove(username);
    }
}