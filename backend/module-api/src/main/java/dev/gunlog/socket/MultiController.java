package dev.gunlog.socket;

import dev.gunlog.enums.Mode;
import dev.gunlog.multi.model.GameRoom;
import dev.gunlog.multi.service.MultiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@Slf4j
@RequiredArgsConstructor
@RestController
public class MultiController {
    private final MultiService multiService;
    private final SimpMessageSendingOperations messageTemplate;

    @MessageMapping("/multi/start")
    public void gameStart(Principal principal) {
        String memberId = principal.getName();
        boolean isStart = multiService.gameStart(memberId);
        Integer roomId = multiService.findRoomId(memberId);
        GameRoom gameRoom = multiService.findRoomByRoomId(roomId);

        if(isStart) {
            messageTemplate.convertAndSend("/sub/room/"+roomId, gameRoom);
            messageTemplate.convertAndSend("/sub/room/"+roomId+"/start", gameRoom.getStartTime());
        }
        if(gameRoom.getGameMode() == Mode.TIME_ATTACK) {
            Thread thread = new TimerThread(roomId, multiService, messageTemplate);
            thread.run();
        }
    }

    @MessageMapping("/multi/init")
    public void getRoomInfo(Integer roomId) {
        GameRoom gameRoom = multiService.findRoomByRoomId(roomId);

        messageTemplate.convertAndSend("/sub/room/"+roomId, gameRoom);
        messageTemplate.convertAndSend("/sub/room/"+roomId+"/start", gameRoom.getStartTime());
    }

    @MessageMapping("/multi/left")
    public void leftMove(Principal principal) {
        String memberId = principal.getName();
        Integer roomId = multiService.findRoomId(memberId);
        GameRoom room = multiService.leftMove(memberId);

        if(room.getStartTime() == null) {
            messageTemplate.convertAndSend("/sub/room/"+roomId+"/start", "");
        }
        messageTemplate.convertAndSend("/sub/room/"+roomId, room);
    }

    @MessageMapping("/multi/right")
    public void rightMove(Principal principal) {
        String memberId = principal.getName();
        Integer roomId = multiService.findRoomId(memberId);
        GameRoom room = multiService.rightMove(memberId);


        if(room.getStartTime() == null) {
            messageTemplate.convertAndSend("/sub/room/"+roomId+"/start", "");
        }
        messageTemplate.convertAndSend("/sub/room/"+roomId, room);
    }

    @MessageMapping("/multi/top")
    public void topMove(Principal principal) {
        String memberId = principal.getName();
        Integer roomId = multiService.findRoomId(memberId);
        GameRoom room = multiService.topMove(memberId);


        if(room.getStartTime() == null) {
            messageTemplate.convertAndSend("/sub/room/"+roomId+"/start", "");
        }
        messageTemplate.convertAndSend("/sub/room/"+roomId, room);
    }

    @MessageMapping("/multi/bottom")
    public void bottomMove(Principal principal) {
        String memberId = principal.getName();
        Integer roomId = multiService.findRoomId(memberId);
        GameRoom room = multiService.bottomMove(memberId);


        if(room.getStartTime() == null) {
            messageTemplate.convertAndSend("/sub/room/"+roomId+"/start", "");
        }
        messageTemplate.convertAndSend("/sub/room/"+roomId, room);
    }
}
