package dev.gunlog.room;

import dev.gunlog.multi.domain.GameRoomRepository;
import dev.gunlog.multi.domain.UserRoomRepository;
import dev.gunlog.multi.model.GameRoom;
import dev.gunlog.multi.model.Player;
import dev.gunlog.multi.service.MultiService;
import dev.gunlog.room.dto.RoomCreateRequestDto;
import dev.gunlog.room.dto.RoomListResponseDto;
import dev.gunlog.room.service.RoomService;
import javassist.tools.web.BadHttpRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
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
    public ResponseEntity<List<RoomListResponseDto>> rooms() {
        List<RoomListResponseDto> result = roomService.getAllRooms();
        result.stream().forEach(dto -> dto.setParticipant(multiService.findRoomByRoomId(dto.getId().intValue()).getPlayers().size()));

        return ResponseEntity.ok(result);
    }
    @PostMapping
    public ResponseEntity<Integer> createRoom(@RequestBody RoomCreateRequestDto requestDto, Principal principal) {
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

        return ResponseEntity.ok(roomId);
    }
    @PutMapping(path = "join/{roomId}")
    public ResponseEntity<String> joinRoom(@PathVariable Integer roomId, Principal principal) throws BadHttpRequest {
        String memberId = principal.getName();

        userRoomRepository.save(memberId, roomId);

        GameRoom gameRoom = gameRoomRepository.findRoomByRoomId(roomId)
                .orElseThrow(() -> new IllegalArgumentException("게임 방을 찾을 수 없습니다. ROOM_ID : "+roomId));
        List<Player> players = gameRoom.getPlayers();

        boolean isJoin = players.stream().anyMatch(player -> player.getNickname().equals(memberId));
        boolean isPeopleLimit = gameRoom.getMaxNumberOfPeople().getSize() == players.size();

        if(isJoin && isPeopleLimit) {
            return new ResponseEntity<>( "FAILURE", HttpStatus.BAD_REQUEST);
        }
        players.add(new Player(memberId));
        return ResponseEntity.ok("SUCCESS");
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