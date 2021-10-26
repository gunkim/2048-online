package dev.gunlog.multi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.gunlog.api.room.domain.enums.Mode;
import dev.gunlog.api.room.domain.enums.Personnel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class GameRoom {
    private String name;
    @Setter
    private List<Player> players;
    private Mode gameMode;
    private Personnel maxNumberOfPeople;
    private boolean isStart;
    @Setter
    @JsonIgnore
    private LocalDateTime startTime;
    private String host;

    @Builder
    public GameRoom(String name, List<Player> players, Mode gameMode, Personnel maxNumberOfPeople, boolean isStart, String host) {
        this.name = name;
        if(players == null) {
            this.players = new LinkedList<>();
        } else {
            this.players = players;
        }
        this.gameMode = gameMode;
        this.maxNumberOfPeople = maxNumberOfPeople;
        this.isStart = isStart;
        this.host = host;
    }
    public void gameStart() {
        this.isStart = true;
        this.startTime = LocalDateTime.now();
    }
    public List<Player> gameStop() {
        List<Player> result = this.players.stream()
                .map(Player::new).collect(Collectors.toList());
        this.isStart = false;
        this.startTime = null;
        this.players.stream().forEach(player -> {
            player.setGameInfo(null);
        });
        return result;
    }
    public void addPlayer(Player player) {
        if(this.maxNumberOfPeople.getSize() <= players.size()) {
            return;
        }
        this.players.add(player);
    }
}
