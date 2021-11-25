package dev.gunlog.room;

import dev.gunlog.common.ApiResponse;
import dev.gunlog.common.WebStatusCode;
import dev.gunlog.multi.domain.GameRoomRepository;
import dev.gunlog.multi.domain.UserRoomRepository;
import dev.gunlog.multi.dto.RoomCreateResponseDto;
import dev.gunlog.multi.model.GameRoom;
import dev.gunlog.multi.model.Player;
import dev.gunlog.multi.service.MultiService;
import dev.gunlog.room.dto.RoomCreateRequestDto;
import dev.gunlog.room.dto.RoomListResponseDto;
import dev.gunlog.room.service.RoomService;
import javassist.tools.web.BadHttpRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2/room")
public class RoomController {
    private final RoomService roomService;
    private final MultiService multiService;
    private final GameRoomRepository gameRoomRepository;
    private final UserRoomRepository userRoomRepository;
    private final SimpMessageSendingOperations messageTemplate;

    @GetMapping(path = "list")
    public ApiResponse<List<RoomListResponseDto>> rooms() throws HttpRequestMethodNotSupportedException {
        List<RoomListResponseDto> result = roomService.getAllRooms();
//        result.stream().forEach(dto -> dto.setParticipant(multiService.findRoomByRoomId(dto.getId().intValue()).getPlayers().size()));

        return ApiResponse.success(result);
    }
    @PostMapping
    public ApiResponse<RoomCreateResponseDto> createRoom(@RequestBody RoomCreateRequestDto requestDto, Principal principal) {
        String memberId = principal.getName();
        Integer roomId = Math.toIntExact(roomService.createRoom(requestDto, memberId));

        GameRoom gameRoom = GameRoom.builder()
                .name(requestDto.getTitle())
                .isStart(false)
                .maxNumberOfPeople(requestDto.getPersonnel())
                .gameMode(requestDto.getMode())
                .host(memberId)
                .build();
        gameRoom.addPlayer(new Player(memberId));
        gameRoomRepository.save(roomId, gameRoom);
        userRoomRepository.save(memberId, roomId);

        return ApiResponse.success(new RoomCreateResponseDto(roomId));
    }
    @PutMapping(path = "join/{roomId}")
    public ApiResponse<String> joinRoom(@PathVariable Integer roomId, Principal principal) throws BadHttpRequest {
        String memberId = principal.getName();

        userRoomRepository.save(memberId, roomId);

        GameRoom gameRoom = gameRoomRepository.findRoomByRoomId(roomId)
                .orElseThrow(() -> new IllegalArgumentException("게임 방을 찾을 수 없습니다. ROOM_ID : "+roomId));
        List<Player> players = gameRoom.getPlayers();

        boolean isJoin = players.stream().anyMatch(player -> player.getNickname().equals(memberId));
        boolean isPeopleLimit = gameRoom.getMaxNumberOfPeople().getSize() == players.size();

        if(isJoin && isPeopleLimit) {
            return ApiResponse.of(WebStatusCode.INVALID_INPUT_VALUE);
        }
        players.add(new Player(memberId));

        messageTemplate.convertAndSend("/sub/room/"+roomId, gameRoom);
        return ApiResponse.success();
    }
    @PutMapping(path = "exit")
    public void exitRoom(Principal principal) {
        String memberId = principal.getName();
        Integer roomId = multiService.findRoomId(memberId);
        multiService.exitRoom(memberId);
        try {
            GameRoom gameRoom = multiService.findRoomByRoomId(roomId);
            messageTemplate.convertAndSend("/sub/room/"+roomId, gameRoom);
        } catch(IllegalArgumentException e) {
            log.info("삭제된 게임 방");
        }
    }
}