package dev.gunlog.game;

import dev.gunlog.single.service.SingleService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
public class SingleController {
    private final SingleService singleService;
    private final SimpMessageSendingOperations messageTemplate;

    @MessageMapping("/single/init")
    public void initGame(Principal principal) {
        String memberId = principal.getName();
        messageTemplate.convertAndSend(String.format("/sub/%s/single/init", memberId), singleService.initGame(memberId));
    }

    @MessageMapping("/single/left")
    public void leftMove(Principal principal) {
        String memberId = principal.getName();
        messageTemplate.convertAndSend(String.format("/sub/%s/single/move/left", memberId), singleService.leftMove(memberId));
    }

    @MessageMapping("/single/right")
    public void rightMove(Principal principal) {
        String memberId = principal.getName();
        messageTemplate.convertAndSend(String.format("/sub/%s/single/move/right", memberId), singleService.rightMove(memberId));
    }

    @MessageMapping("/single/top")
    public void topMove(Principal principal) {
        String memberId = principal.getName();
        messageTemplate.convertAndSend(String.format("/sub/%s/single/move/top", memberId), singleService.topMove(memberId));
    }

    @MessageMapping("/single/bottom")
    public void bottomMove(Principal principal) {
        String memberId = principal.getName();
        messageTemplate.convertAndSend(String.format("/sub/%s/single/move/bottom", memberId), singleService.bottomMove(memberId));
    }
}