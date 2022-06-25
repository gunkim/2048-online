package io.github.gunkim.room;

import io.github.gunkim.common.ApiResponse;
import io.github.gunkim.multi.domain.GameRoom;
import io.github.gunkim.multi.dto.RoomCreateRequestDto;
import io.github.gunkim.multi.dto.RoomCreateResponseDto;
import io.github.gunkim.multi.service.MultiService;
import io.github.gunkim.multi.dto.RoomListResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2/room")
public class RoomController {
    private final MultiService multiService;
    private final SimpMessageSendingOperations messageTemplate;

    @GetMapping(path = "list")
    public ApiResponse<List<RoomListResponseDto>> rooms(){
        List<RoomListResponseDto> result = multiService.getAllRooms();
        return ApiResponse.success(result);
    }
    @PostMapping
    public ApiResponse<RoomCreateResponseDto> createRoom(@RequestBody RoomCreateRequestDto requestDto, Principal principal) {
        String nickname = principal.getName();
        Long roomId = multiService.createRoom(requestDto, nickname);

        messageTemplate.convertAndSend("/sub/rooms", multiService.getAllRooms());
        return ApiResponse.success(new RoomCreateResponseDto(roomId));
    }
    @PutMapping(path = "join/{roomId}")
    public ApiResponse<String> joinRoom(@PathVariable Long roomId, Principal principal) {
        String nickname = principal.getName();
        sendSocketData(multiService.joinRoom(roomId, nickname));

        return ApiResponse.success();
    }
    @PutMapping(path = "exit")
    public ApiResponse exitRoom(Principal principal) {
        String nickname = principal.getName();
        sendSocketData(multiService.exitRoom(nickname));

        return ApiResponse.success();
    }
    private void sendSocketData(GameRoom room) {
        if(room.getId() != -1l) {
            messageTemplate.convertAndSend("/sub/room/"+room.getId(), room);
        }
    }
}