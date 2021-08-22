package dev.gunlog.game;

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

    @MessageMapping("/multi/init")
    public void getRoomInfo(Integer roomId) {
        messageTemplate.convertAndSend("/sub/room/"+roomId, multiService.findRoomByRoomId(roomId));
    }

    @MessageMapping("/multi/left")
    public void leftMove(Principal principal) {
        String memberId = principal.getName();
        Integer roomId = multiService.findRoomId(memberId);
        messageTemplate.convertAndSend("/sub/room/"+roomId, multiService.leftMove(memberId));
    }

    @MessageMapping("/multi/right")
    public void rightMove(Principal principal) {
        String memberId = principal.getName();
        Integer roomId = multiService.findRoomId(memberId);
        messageTemplate.convertAndSend("/sub/room/"+roomId, multiService.rightMove(memberId));
    }

    @MessageMapping("/multi/top")
    public void topMove(Principal principal) {
        String memberId = principal.getName();
        Integer roomId = multiService.findRoomId(memberId);
        messageTemplate.convertAndSend("/sub/room/"+roomId, multiService.topMove(memberId));
    }

    @MessageMapping("/multi/bottom")
    public void bottomMove(Principal principal) {
        String memberId = principal.getName();
        Integer roomId = multiService.findRoomId(memberId);
        messageTemplate.convertAndSend("/sub/room/"+roomId, multiService.bottomMove(memberId));
    }
}
