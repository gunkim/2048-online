package dev.gunlog.room;

import dev.gunlog.common.ApiResponse;
import dev.gunlog.multi.domain.GameRoomRedis;
import dev.gunlog.multi.dto.RoomCreateResponseDto;
import dev.gunlog.multi.service.MultiService;
import dev.gunlog.room.dto.RoomCreateRequestDto;
import dev.gunlog.room.dto.RoomListResponseDto;
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

        return ApiResponse.success(new RoomCreateResponseDto(roomId));
    }
    @PutMapping(path = "join/{roomId}")
    public ApiResponse<String> joinRoom(@PathVariable Long roomId, Principal principal) {
        String nickname = principal.getName();

        GameRoomRedis room = multiService.joinRoom(roomId, nickname);

        messageTemplate.convertAndSend("/sub/room/"+roomId, room);
        return ApiResponse.success();
    }
    @PutMapping(path = "exit")
    public void exitRoom(Principal principal) {
        String nickname = principal.getName();
        Long roomId = multiService.exitRoom(nickname);
        if(roomId != -1) {
            GameRoomRedis room = multiService.findRoomByRoomId(roomId);
            messageTemplate.convertAndSend("/sub/room/"+roomId, room);
        }
    }
}