package dev.gunlog.socket;

import dev.gunlog.model.GameMode;
import dev.gunlog.model.Room;
import dev.gunlog.model.RoomManager;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomController {
    @MessageMapping("/rooms")
    @SendTo("/play/rooms")
    public RoomManager getRooms() {
        RoomManager roomManager = RoomManager.getInstance();
        roomManager.addRoom(Room.builder()
                .name("안녕하세요1")
                .isStart(false)
                .gameMode(GameMode.SURVIVAL)
                .build());
        roomManager.addRoom(Room.builder()
                .name("안녕하세요2")
                .isStart(false)
                .gameMode(GameMode.TIME_ATTACK)
                .build());
        return roomManager;
    }
}
