package dev.gunlog.socket;

import dev.gunlog.multi.domain.GameRoom;
import dev.gunlog.multi.service.MultiService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

@Slf4j
@RequiredArgsConstructor
public class TimerThread extends Thread {
    private final Long roomId;
    private final MultiService multiService;
    private final SimpMessageSendingOperations messageTemplate;

    @SneakyThrows
    @Override
    public void run() {
        Thread.sleep(180000); //3분
        multiService.gameStop(this.roomId);
        GameRoom room = multiService.findRoomByRoomId(this.roomId);
        messageTemplate.convertAndSend("/sub/room/"+roomId+"/result", room.getPlayers());
        messageTemplate.convertAndSend("/sub/room/"+roomId, room);
    }
}