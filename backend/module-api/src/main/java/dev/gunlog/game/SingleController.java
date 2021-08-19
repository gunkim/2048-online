package dev.gunlog.game;

import dev.gunlog.multi.model.Game;
import dev.gunlog.single.service.SingleService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
public class SingleController {
    private final SingleService singleService;

    @MessageMapping("/start")
    @SendTo("/play/start")
    public Game boradCast(Principal principal) {
        return singleService.initGame(principal.getName());
    }

    @MessageMapping("/left")
    @SendTo("/play/left")
    public Game leftMove(Principal principal) {
        return singleService.leftMove(principal.getName());
    }

    @MessageMapping("/right")
    @SendTo("/play/right")
    public Game rightMove(Principal principal) {
        return singleService.rightMove(principal.getName());
    }

    @MessageMapping("/top")
    @SendTo("/play/top")
    public Game topMove(Principal principal) {
        return singleService.topMove(principal.getName());
    }

    @MessageMapping("/bottom")
    @SendTo("/play/bottom")
    public Game bottomMove(Principal principal) {
        return singleService.bottomMove(principal.getName());
    }
}
