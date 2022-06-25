package io.github.gunkim.config;

import io.github.gunkim.multi.domain.GameRoom;
import io.github.gunkim.multi.service.MultiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.AbstractSubProtocolEvent;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketEventListener {
    public static Set<String> players = new HashSet<>();
    private final MultiService multiService;
    private final SimpMessageSendingOperations messageTemplate;
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        String username = getUsername(event);
        players.add(username);

        messageTemplate.convertAndSend("/sub/rooms/players", players);
        log.info("소켓 연결 : "+username);
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        String username = getUsername(event);
        players.remove(username);
        GameRoom room = multiService.exitRoom(username);
        messageTemplate.convertAndSend("/sub/rooms/players", players);
        messageTemplate.convertAndSend("/sub/rooms", multiService.getAllRooms());
        if(room.getId() != -1l) {
            messageTemplate.convertAndSend("/sub/room/"+room.getId(), room);
        }
        log.info("소켓 해지 : "+username);
    }
    private String getUsername(AbstractSubProtocolEvent event) {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) event.getMessage().getHeaders().get("simpUser");
        return token.getName();
    }
}
