package dev.gunlog.model;

import dev.gunlog.enums.Mode;
import dev.gunlog.enums.Personnel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class Room {
    private String name;
    private List<Player> players = new LinkedList<>();
    private Mode gameMode;
    private Personnel maxNumberOfPeople;
    private boolean isStart;
    private int timer;

    @Builder
    public Room(String name, Mode gameMode, Personnel maxNumberOfPeople, boolean isStart, int timer) {
        this.name = name;
        this.gameMode = gameMode;
        this.maxNumberOfPeople = maxNumberOfPeople;
        this.isStart = isStart;
        this.timer = timer;
    }
    public void addPlayer(Player player) {
        if(this.maxNumberOfPeople.getSize() <= players.size()) {
            return;
        }
        this.players.add(player);
    }
}
