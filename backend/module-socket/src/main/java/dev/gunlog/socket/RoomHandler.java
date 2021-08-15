package dev.gunlog.socket;

import dev.gunlog.model.Room;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class RoomHandler {
    public static final Map<Integer, Room> roomManagerMap = new HashMap<>();
    public static final Map<String, Integer> userRoomMap = new HashMap<>();

    @MessageMapping("/room")
    @SendTo("/play/room")
    public Room getRoomInfo(Integer roomId) {
        return roomManagerMap.get(roomId);
    }
}
