package models;

import jdk.jshell.Snippet;

import java.util.ArrayList;
import java.util.IllformedLocaleException;
import java.util.List;

public class Room {
       private int roomNumber;
       private RoomType type;
       private double pricePerNight;
       private RoomStatus status;

    public Room(int roomNumber, RoomType roomType, double pricePerNight, RoomStatus status) {
            this.roomNumber = roomNumber;
            this.type = type;
            this.pricePerNight = pricePerNight;
            this.status = status;
    }

    public int getRoomNumber() {
            return roomNumber;
        }

        public RoomStatus getStatus() {
            return status;
        }

        public void setStatus(RoomStatus status) {
            if (status == null) {
                throw new IllegalArgumentException("Status cannot be null.");
            }
            this.status = status;
        }

        public double getPricePerNight() {
            return pricePerNight;
        }

    public static List<Room> rooms = new ArrayList<>();

        @Override
        public String toString() {
            return "Room " + roomNumber + " | Type: " + type + " | Price Per Night: R$" + pricePerNight + " | Status: " + status;
        }
}

