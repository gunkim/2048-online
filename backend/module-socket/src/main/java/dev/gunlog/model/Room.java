package dev.gunlog.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class Room implements Serializable {
    private String name;
    private List<Player> players = new LinkedList<>();
    private GameMode gameMode;
    private boolean isStart;
    private int timer;

    @Builder
    public Room(String name, boolean isStart, int timer, GameMode gameMode) {
        this.name = name;
        this.isStart = isStart;
        this.timer = timer;
        this.gameMode = gameMode;
    }
    public void addPlayer(Player player) {
        this.players.add(player);
    }
}
