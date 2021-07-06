package dev.gunlog.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class Room {
    private String name;
    private List<Player> players = new LinkedList<>();
    private GameMode gameMode;
    private MaxNumberOfPeople maxNumberOfPeople;
    private boolean isStart;
    private int timer;

    @Builder
    public Room(String name, GameMode gameMode, MaxNumberOfPeople maxNumberOfPeople, boolean isStart, int timer) {
        this.name = name;
        this.gameMode = gameMode;
        this.maxNumberOfPeople = maxNumberOfPeople;
        this.isStart = isStart;
        this.timer = timer;
    }

    public Integer getPlayers() {
        return this.players.size();
    }

    public String getGameMode() {
        return gameMode.getTitle();
    }
    public Integer getMaxNumberOfPeople() {
        return maxNumberOfPeople.getValue();
    }

    public void addPlayer(Player player) {
        if(players.size() == maxNumberOfPeople.getValue()) {
            return;
        }
        player.enterRoom(this);
        this.players.add(player);
    }
}
