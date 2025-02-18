package functions;

import database.DataBaseConnection;
import models.Room;
import models.RoomStatus;

import static models.Room.rooms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class UpdateRoomStats {
      public static void UpdateRoomStats (Scanner sc) {
          System.out.print("Rooms registered in the system: ");
          System.out.print("");

          if (rooms.isEmpty()) {
              System.out.println("No rooms registered.");
              return;
          }

          for (Room room : rooms) {
              System.out.println("Room " + room.getRoomNumber() + " - Status: " + room.getStatus());
          }
          System.out.print("Enter room number to update status:");
          int roomNumber = sc.nextInt();
          sc.nextLine();

          Room room = rooms.stream()
                  .filter(p -> p.getRoomNumber() == roomNumber)
                  .findFirst()
                  .orElse(null);

          if (room != null) {
              System.out.println("Current stats: " + room.getStatus());
              System.out.println("New stats (Single / Double / Suite): ");
              String newStats = sc.nextLine().trim().toUpperCase();

              try {
                  RoomStatus statusEnum = RoomStatus.valueOf(newStats);
                  room.setStatus(statusEnum);
                  System.out.print("Room stats updated successfully!");
              } catch (IllegalArgumentException e) {
                  System.out.println("Invalid status! Please enter SINGLE, DOUBLE, or SUITE.");
              }
              } else {
                  System.out.println("Room not foun");
              }
          }

          private static void updateRoomStatsInDataBase(int roomNumber, RoomStatus status) {
          String sql = "UPDATE registerroom SET roomStatus = ? WHERE roomNumber = ?";

          try (Connection connection = DataBaseConnection.conect();
              PreparedStatement stmt = connection.prepareStatement(sql)) {

                  stmt.setString(1, status.name());
                  stmt.setString(2, String.valueOf(roomNumber));

                  int rowsAffected = stmt.executeUpdate();

                  if (rowsAffected == 0) {
                      System.out.println("No records updated. Check if the room exists in the database.");
                  }
              } catch (SQLException e) {
                  System.out.println("Error updating room status: " + e.getMessage());
              }
          }
      }
