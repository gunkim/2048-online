package dev.gunlog.room;

import dev.gunlog.model.Player;
import dev.gunlog.model.Room;
import dev.gunlog.repository.GameRoomRepository;
import dev.gunlog.repository.UserRoomRepository;
import dev.gunlog.room.dto.RoomCreateRequestDto;
import dev.gunlog.room.dto.RoomListResponseDto;
import dev.gunlog.room.service.RoomService;
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
    private final GameRoomRepository gameRoomRepository;
    private final UserRoomRepository userRoomRepository;

    @GetMapping(path = "list")
    public ResponseEntity<List<RoomListResponseDto>> rooms() {
        List<RoomListResponseDto> result = roomService.getAllRooms();

        return ResponseEntity.ok(result);
    }
    @PostMapping
    public ResponseEntity<Integer> createRoom(@RequestBody RoomCreateRequestDto requestDto, Principal principal) {
        String username = principal.getName();
        Integer roomId = Math.toIntExact(roomService.createRoom(requestDto, username));

        Room room = Room.builder()
                .name(requestDto.getTitle())
                .isStart(false)
                .maxNumberOfPeople(requestDto.getPersonnel())
                .gameMode(requestDto.getMode())
                .timer(0)
                .build();
        room.addPlayer(new Player(username));
        gameRoomRepository.save(roomId, room);
        userRoomRepository.save(username, roomId);

        return ResponseEntity.ok(roomId);
    }
    @PutMapping(path = "join/{roomId}")
    public void joinRoom(@PathVariable Integer roomId, Principal principal) {
        String username = principal.getName();
        boolean isUserJoin = userRoomRepository.findRoomIdByUsername(username) != null;

        if(isUserJoin) {
            return;
        }
        userRoomRepository.save(username, roomId);

        Room room = gameRoomRepository.findRoomByRoomId(roomId)
                .orElseThrow(() -> new IllegalArgumentException("게임 방을 찾을 수 없습니다. ROOM_ID : "+roomId));
        room.getPlayers().add(new Player(username));
    }
}