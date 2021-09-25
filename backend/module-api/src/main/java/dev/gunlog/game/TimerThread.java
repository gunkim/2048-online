package dev.gunlog.game;

import dev.gunlog.multi.model.GameRoom;
import dev.gunlog.multi.model.Player;
import dev.gunlog.multi.service.MultiService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class TimerThread extends Thread {
    private final int roomId;
    private final MultiService multiService;
    private final SimpMessageSendingOperations messageTemplate;

    @SneakyThrows
    @Override
    public void run() {
        Thread.sleep(5000);//180000); //3분
        List<Player> players = multiService.gameStop(this.roomId);
        GameRoom room = multiService.findRoomByRoomId(this.roomId);
        messageTemplate.convertAndSend("/sub/room/"+roomId+"/result", players);
        messageTemplate.convertAndSend("/sub/room/"+roomId, room);
    }
}