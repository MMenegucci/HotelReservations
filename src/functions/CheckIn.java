package functions;

import database.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class CheckIn {
    public static void checkIn(Scanner sc) {
        Connection conn = null;
        try {
            conn = DataBaseConnection.conect();

            if (conn == null) {
                System.out.println("Error: Database connection failed.");
                return;
            }

            System.out.println("\n--- Enter the details below to check-in ---");
            System.out.print("Enter the guest's CPF: ");
            String cpf = sc.nextLine().trim();

            String checkReservationQuery = "SELECT id, guest_cpf, room_number, checkin_date, checkout_date, pricePerNight, status " +
                    "FROM reservations WHERE guest_cpf = ? AND status IN ('CONFIRMED', 'PENDING')";

            try (PreparedStatement checkGuestStmt = conn.prepareStatement(checkReservationQuery)) {
                checkGuestStmt.setString(1, cpf);
                try (ResultSet rs = checkGuestStmt.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("\nNo active reservation found for this CPF. Please make a reservation first.");
                        return;
                    }

                    int id = rs.getInt("id");
                    int roomNumber = rs.getInt("room_number");
                    LocalDate checkInDate = rs.getDate("checkin_date").toLocalDate();
                    LocalDate checkOutDate = rs.getDate("checkout_date").toLocalDate();
                    double pricePerNight = rs.getDouble("pricePerNight");
                    String status = rs.getString("status");

                    long days = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
                    days = Math.max(days, 1);

                    double totalCost = days * pricePerNight;

                    String updateStatusQuery = "UPDATE registerroom SET roomStatus = 'OCCUPIED' WHERE roomNumber = ?";
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateStatusQuery)) {
                        updateStmt.setInt(1, roomNumber);
                        updateStmt.executeUpdate();
                    }

                    String insertCheckinQuery = "INSERT INTO checkins (id, guest_cpf, room_number, checkin_date, days, totalCost, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
                    int rowsInsert;
                    try (PreparedStatement stmt = conn.prepareStatement(insertCheckinQuery)) {
                        stmt.setInt(1, id);
                        stmt.setString(2, cpf);
                        stmt.setInt(3, roomNumber);
                        stmt.setObject(4, LocalDateTime.now());
                        stmt.setInt(5, (int) days);
                        stmt.setDouble(6, totalCost);
                        stmt.setString(7, status);

                        rowsInsert = stmt.executeUpdate();
                        if (rowsInsert > 0) {
                            System.out.println("\nCheck-in carried out successfully!");
                            System.out.println("Room Number: " + roomNumber);
                            System.out.println("Check-in Date: " + checkInDate);
                            System.out.println("Check-out Date: " + checkOutDate);
                            System.out.println("Total Nights: " + days);
                            System.out.println("Total Cost: $" + totalCost);
                        } else {
                            System.out.println("Error: Unable to create checkin.");
                        }
                    }
                }
            } catch (SQLException e) {
                System.out.println("Error during check-in: " + e.getMessage());
            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        System.out.println("Error closing the connection: " + e.getMessage());
                    }
                }
            }
        } finally {
        }
    }
}