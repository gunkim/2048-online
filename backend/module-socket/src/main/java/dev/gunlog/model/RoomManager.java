package dev.gunlog.model;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Getter
public class RoomManager {
    private List<Room> roomList;

    public static RoomManager getInstance() {
        return new RoomManager();
    }

    private RoomManager() {
        this.roomList = new LinkedList<>();
    }
    public void addRoom(Room room) {
        this.roomList.add(room);
    }
}
