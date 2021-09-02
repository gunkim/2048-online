package dev.gunlog.game;

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
        Thread.sleep(180000); //3분
        multiService.gameStop(this.roomId);
        List<Player> playerList = multiService.findRoomByRoomId(this.roomId).getPlayers();
        messageTemplate.convertAndSend("/sub/room/"+roomId+"/stop", playerList);
    }
}