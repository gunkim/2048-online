package dev.gunlog.socket;

import dev.gunlog.dto.RoomCreateRequestDto;
import dev.gunlog.model.*;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class RoomController {
    private static RoomManager roomManager = new RoomManager();
    @MessageMapping("/rooms")
    @SendTo("/play/rooms")
    public RoomManager getRooms() {
        return roomManager;
    }
    @MessageMapping("/room")
    @SendTo("/game/rooms")
    public void createRoom(RoomCreateRequestDto dto, Principal principal) {
        String name = principal.getName();
        System.out.println("방 생성");
        Room room = dto.toRoom();
        room.addPlayer(new Player(name, room));
        roomManager.addRoom(room);
    }
}
