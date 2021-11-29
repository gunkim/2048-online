package dev.gunlog.socket;

import dev.gunlog.multi.domain.GameRoomRedis;
import dev.gunlog.room.domain.enums.Mode;
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
        String nickname = principal.getName();
        socketSendData(multiService.gameStart(nickname));
    }
    @MessageMapping("/multi/ready")
    public void gameReady(Principal principal) {
        String nickname = principal.getName();
        socketSendData(multiService.ready(nickname));
    }

    @MessageMapping("/multi/init")
    public void getRoomInfo(Principal principal) {
        String nickname = principal.getName();
        socketSendData(multiService.findRoomByNickname(nickname));
    }

    @MessageMapping("/multi/left")
    public void leftMove(Principal principal) {
        String nickname = principal.getName();
        socketSendData(multiService.leftMove(nickname));
    }

    @MessageMapping("/multi/right")
    public void rightMove(Principal principal) {
        String nickname = principal.getName();
        socketSendData(multiService.rightMove(nickname));
    }

    @MessageMapping("/multi/top")
    public void topMove(Principal principal) {
        String nickname = principal.getName();
        socketSendData(multiService.topMove(nickname));
    }

    @MessageMapping("/multi/bottom")
    public void bottomMove(Principal principal) {
        String nickname = principal.getName();
        socketSendData(multiService.bottomMove(nickname));
    }
    private void socketSendData(GameRoomRedis room) {
        String targetPoint = String.format("/sub/room/%d", room.getId());
        messageTemplate.convertAndSend(targetPoint, room);
    }
}
