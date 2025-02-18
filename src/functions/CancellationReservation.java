package functions;

import database.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Scanner;

public class CancellationReservation {
    public static void cancelReservation(Scanner sc) {
        try (Connection conn = DataBaseConnection.conect()) {

            System.out.print("Enter the CPF of the person who made the reservation: ");
            String cpf = sc.nextLine();

            String query = "SELECT id, roomNumber, type, status FROM registerroom WHERE cpf = ? AND status = 'OCCUPIED'";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, cpf);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        int id = rs.getInt("id");
                        int roomNumber = rs.getInt("roomNumber");
                        String type = rs.getString("type");
                        String status = rs.getString("status");

                        System.out.println("\nReserved room found");
                        System.out.println("ID: " + id + " | Number: " + roomNumber + " | Type: " + type + " | Status: " + status);

                        System.out.print("\nDo you want to cancel this reservation? (yes/no): ");
                        String confirmation = sc.nextLine().trim().toLowerCase(Locale.ROOT);

                        if (confirmation.equals("yes")) {

                            String updateQuery = "UPDATE registerroom SET status = 'AVAILABLE', cpf = NULL WHERE id = ?";
                            try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                                updateStmt.setInt(1, id);
                                int rowsUpdated = updateStmt.executeUpdate();

                                if (rowsUpdated > 0) {
                                    System.out.println("\nReservation canceled successfully!");
                                } else {
                                    System.out.println("\nError: Could not cancel the reservation.");
                                }
                            }
                        } else {
                            System.out.println("\nOperation canceled. The reservation remains active.");
                        }
                        } else {
                            System.out.println("\nNo reservations found for this CPF or the room is not occupied.");
                        }
                    }
                }
            } catch (SQLException e) {
                System.out.println("\nError when canceling the reservation " + e.getMessage());
            }
        }
    }

