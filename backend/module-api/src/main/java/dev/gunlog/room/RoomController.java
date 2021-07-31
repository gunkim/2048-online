package dev.gunlog.room;

import dev.gunlog.room.dto.RoomCreateRequestDto;
import dev.gunlog.room.dto.RoomListResponseDto;
import dev.gunlog.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity createRoom(RoomCreateRequestDto requestDto, Principal principal) {
        roomService.createRoom(requestDto, principal.getName());

        return ResponseEntity.ok().build();
    }
}