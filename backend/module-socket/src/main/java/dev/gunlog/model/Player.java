package dev.gunlog.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@ToString
public class Player implements Serializable {
    private String nickname;
    private Room room;

    @Builder
    public Player(String nickname, Room room) {
        this.nickname = nickname;
        this.room = room;
    }

    public void enterRoom(Room room) {
        this.room = room;
    }
}
