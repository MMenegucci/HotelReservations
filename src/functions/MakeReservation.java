package functions;

import database.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class MakeReservation {
    public static void reserveRoom(Scanner sc) {
        try (Connection conn = DataBaseConnection.conect()) {

            String query = "SELECT id, roomNumber, type, pricePerNight FROM registerroom WHERE status = 'AVAILABLE'";
            try (PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {

                System.out.println("Available rooms: ");
                boolean hasRooms = false;
                while (rs.next()) {
                    hasRooms = true;
                    int id = rs.getInt("id");
                    int roomNumber = rs.getInt("roomNumber");
                    String type = rs.getString("type");
                    double price = rs.getDouble("pricePerNight");

                    System.out.println("ID: " + id + " | Number: " + roomNumber + " | Type: " + type + " | Price: $ " + price);
                }
            }

            System.out.print("Enter the room number you want to book:");
               int roomId = sc.nextInt();
               sc.nextLine();

               String updateQuery = "UPDATE rooms SET status = 'OCCUPIED' WHERE id = ?";
               try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                   updateStmt.setInt(1, roomId);
                   int rowsUpdated = updateStmt.executeUpdate();

                   if (rowsUpdated > 0) {
                       System.out.println("Room booked successfully!");
                   } else {
                       System.out.println("Error: Invalid ID or room already occupied.");
                   }
               }
        } catch (SQLException e) {
            System.out.println("Error when booking the room: " + e.getMessage());
        }
    }
}
