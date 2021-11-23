package dev.gunlog.socket;

import dev.gunlog.config.WebSocketEventListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class WaitingRoomController {
    private final SimpMessageSendingOperations messageTemplate;

    @MessageMapping("/rooms")
    public void getPlayers() {
        messageTemplate.convertAndSend("/sub/rooms", WebSocketEventListener.players);
    }
}
