package dev.gunlog.model;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Getter
public class RoomManager {
    private List<Room> roomList;

    public RoomManager() {
        this.roomList = new LinkedList<>();
    }
    public void addRoom(Room room) {
        this.roomList.add(room);
    }
}
