package dev.gunlog.room;

import dev.gunlog.enums.Mode;
import dev.gunlog.enums.Personnel;
import dev.gunlog.model.Player;
import dev.gunlog.model.Room;
import dev.gunlog.room.dto.RoomCreateRequestDto;
import dev.gunlog.room.dto.RoomListResponseDto;
import dev.gunlog.room.service.RoomService;
import dev.gunlog.socket.RoomHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2/room")
public class RoomController {
    private final RoomService roomService;

    @GetMapping(path = "list")
    public ResponseEntity<List<RoomListResponseDto>> rooms() {
        List<RoomListResponseDto> result = roomService.getAllRooms();

        return ResponseEntity.ok(result);
    }
    @PostMapping
    public ResponseEntity<Integer> createRoom(@RequestBody RoomCreateRequestDto requestDto, Principal principal) {
        log.info("DTO : "+requestDto);
        String username = principal.getName();
        Integer roomId = Math.toIntExact(roomService.createRoom(requestDto, username));
        Room room = Room.builder()
                .name("새로운 방입니당~")
                .isStart(false)
                .maxNumberOfPeople(Personnel.FOUR)
                .gameMode(Mode.SPEED_ATTACK)
                .timer(0)
                .build();
        room.addPlayer(new Player(username));
        RoomHandler.roomManagerMap.put(roomId, room);
        RoomHandler.userRoomMap.put(username, roomId);

        return ResponseEntity.ok(roomId);
    }
    @PutMapping(path = "join/{roomId}")
    public void joinRoom(@PathVariable Integer roomId, Principal principal) {
        String username = principal.getName();
        RoomHandler.userRoomMap.put(username, roomId);
        Room room = RoomHandler.roomManagerMap.get(roomId);
        room.getPlayers().add(new Player(username));
    }
}