package dev.gunlog.multi.domain;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRoomRepository {
    private static final Map<String, Integer> userRoomInfoMap = new HashMap<>();

    public Optional<Integer> findRoomIdByMemberId(String memberId) {
        return Optional.ofNullable(userRoomInfoMap.get(memberId));
    }
    public void save(String memberId, Integer roomId) {
        userRoomInfoMap.put(memberId, roomId);
    }

    public void deleteByMemberId(String memberId) {
        userRoomInfoMap.remove(memberId);
    }
}