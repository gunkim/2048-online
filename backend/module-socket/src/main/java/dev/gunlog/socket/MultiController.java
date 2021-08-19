package dev.gunlog.socket;

import dev.gunlog.model.Room;
import dev.gunlog.service.MultiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@Slf4j
@RequiredArgsConstructor
@RestController
public class MultiController {
    private final MultiService multiService;

    @MessageMapping("/room")
    @SendTo("/play/room")
    public Room getRoomInfo(Integer roomId) {
        return multiService.findRoomByRoomId(roomId);
    }
    @MessageMapping("/multi/left")
    @SendTo("/play/multi/left")
    public Room leftMove(Principal principal) {
        log.info("왼쪽 이동 호출");
        return multiService.leftMove(principal.getName());
    }

    @MessageMapping("/multi/right")
    @SendTo("/play/multi/right")
    public Room rightMove(Principal principal) {
        log.info("오른쪽 이동 호출");
        return multiService.rightMove(principal.getName());
    }

    @MessageMapping("/multi/top")
    @SendTo("/play/multi/top")
    public Room topMove(Principal principal) {
        log.info("위쪽 이동 호출");
        return multiService.topMove(principal.getName());
    }

    @MessageMapping("/multi/bottom")
    @SendTo("/play/multi/bottom")
    public Room bottomMove(Principal principal) {
        log.info("아래쪽 이동 호출");
        return multiService.bottomMove(principal.getName());
    }
}
