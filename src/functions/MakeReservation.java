package functions;

import database.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MakeReservation {
    public static void reserveRoom(Scanner sc) {
        try (Connection conn = DataBaseConnection.conect()) {

            System.out.print("Enter your CPF: ");
            String cpf = sc.nextLine().trim();

            String checkGuestQuery = "SELECT * FROM guests WHERE cpf = ?";
            try (PreparedStatement checkGuestStmt = conn.prepareStatement(checkGuestQuery)) {
                checkGuestStmt.setString(1, cpf);
                try (ResultSet rs = checkGuestStmt.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("\nNo guest found with this CPF. Please register first.");
                        return;
                    }
                }
            }

            System.out.print("\nEnter the room type to search for (e.g, Single, Double, Suite): ");
            String type = sc.nextLine();

            String query = "SELECT id, roomNumber, type, pricePerNight FROM registerroom WHERE status = 'AVAILABLE' AND type = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, type);

                try (ResultSet rs = stmt.executeQuery()) {
                    System.out.println("\nAvailable rooms of type: " + type);
                    boolean hasRooms = false;

                    while (rs.next()) {
                        hasRooms = true;
                        int id = rs.getInt("id");
                        int roomNumber = rs.getInt("roomNumber");
                        double price = rs.getDouble("pricePerNight");

                        System.out.println("ID: " + id + " | Number: " + roomNumber + " | Price: $ " + price);
                    }

                    if (!hasRooms) {
                        System.out.println("No rooms available at the moment.");
                        return;
                    }
                }
            }

            int roomId;
            while (true) {
                try {
                    System.out.print("\nEnter the ID of the room you want to reserve:");
                    roomId = sc.nextInt();
                    sc.nextLine();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid numeric room ID.");
                    sc.nextLine();
                }
            }

            String updateQuery = "UPDATE registerroom SET status = 'OCCUPIED' WHERE id = ?";
            try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                updateStmt.setString(1, cpf);
                updateStmt.setInt(2, roomId);
                int rowsUpdated = updateStmt.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("Room booked successfully!");
                } else {
                    System.out.println("Error: Unable to book the room. Please try again.");
                }
            }

                } catch (SQLException e) {
                    System.out.println("Error when booking the room: " + e.getMessage());
                }
            }
        }
