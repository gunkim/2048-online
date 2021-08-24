package dev.gunlog.multi.model;

import dev.gunlog.enums.Mode;
import dev.gunlog.enums.Personnel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Getter
public class GameRoom {
    private String name;
    @Setter
    private List<Player> players;
    private Mode gameMode;
    private Personnel maxNumberOfPeople;
    private boolean isStart;
    private int timer;
    private String host;

    @Builder
    public GameRoom(String name, List<Player> players, Mode gameMode, Personnel maxNumberOfPeople, boolean isStart, int timer, String host) {
        this.name = name;
        if(players == null) {
            this.players = new LinkedList<>();
        } else {
            this.players = players;
        }
        this.gameMode = gameMode;
        this.maxNumberOfPeople = maxNumberOfPeople;
        this.isStart = isStart;
        this.timer = timer;
        this.host = host;
    }
    public void gameStart() {
        this.isStart = true;
    }
    public void addPlayer(Player player) {
        if(this.maxNumberOfPeople.getSize() <= players.size()) {
            return;
        }
        this.players.add(player);
    }
}
