package functions;

import database.DataBaseConnection;
import models.Room;
import models.RoomStatus;
import models.RoomType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import static models.Room.rooms;

public class RegisterRoom {
    public static void registerRoom(Scanner sc) {

        int roomNumber = 0;
        RoomType roomType = null;
        double pricePerNight = 0.0;
        RoomStatus roomStatus = null;

        try {
            System.out.print("Enter the room number : ");
            roomNumber = sc.nextInt();
            sc.nextLine();

            while (roomType == null) {
                try {
                    System.out.print("Enter the room type (Single, Double or Suite): ");
                    String type = sc.nextLine().toUpperCase();
                    roomType = RoomType.valueOf(type);
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid room type. Please enter Single, Double, or Suite.");
                }
            }

            System.out.print("Enter the price per night: ");
            pricePerNight = sc.nextDouble();
            sc.nextLine();

            while (roomStatus == null) {
                try {
                    System.out.print("Enter the status (Available, Occupied or Maintenance): ");
                    String status = sc.nextLine().toUpperCase();
                    roomStatus = RoomStatus.valueOf(status);
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid room status. Please enter Available, Occupied or Maintenance.");
                }
            }

            Room room = new Room(roomNumber, roomType, pricePerNight, roomStatus);
            rooms.add(room);

            System.out.println();
            System.out.println("Room successfully registered!");

        } catch (Exception e) {
            System.out.println("An error occurred. Please try again.");
            return;
        }

        String sql = "INSERT INTO registerroom (roomNumber, type, pricePerNight, roomStatus) VALUES (?, ?, ?, ?)";

        try (Connection conect = DataBaseConnection.conect();
             PreparedStatement stmt = conect.prepareStatement(sql)) {

            stmt.setInt(1, roomNumber);
            stmt.setString(2, roomType.name());
            stmt.setDouble(3, pricePerNight);
            stmt.setString(4, roomStatus.name());

            stmt.executeUpdate();

            System.out.println("Room successfully saved in database!");
        } catch (SQLException e) {
            System.out.println("Error registering room. " + e.getMessage());
        }
    }
}
