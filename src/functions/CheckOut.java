package functions;

import database.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class CheckOut {
    public static void checkOut(Scanner sc) {
        Connection conn = null;
        try {
            conn = DataBaseConnection.conect();

            if (conn == null) {
                System.out.println("Error: Database connection failed.");
                return;
            }

            conn.setAutoCommit(false);

            System.out.print("Enter the room number for checkout: ");
            int roomNumber = sc.nextInt();
            sc.nextLine();

            String checkReservationQuery = "SELECT id, guest_cpf, room_number, checkin_date, checkout_date, status " +
                    "FROM reservations " +
                    "WHERE room_number = ? AND status IN ('CONFIRMED', 'PENDING')";

            try (PreparedStatement checkReservationStmt = conn.prepareStatement(checkReservationQuery)) {

                checkReservationStmt.setInt(1, roomNumber);

                try (ResultSet rs = checkReservationStmt.executeQuery()) {
                    if (rs.next()) {
                        int id = rs.getInt("id");
                        String guestCpf = rs.getString("guest_cpf");
                        String checkinDate = rs.getString("checkin_date");
                        String checkoutDate = rs.getString("checkout_date");
                        String status = rs.getString("status");

                        System.out.println("\nReservation found:");
                        System.out.println("Reservation ID: " + id);
                        System.out.println("Guest CPF: " + guestCpf);
                        System.out.println("Room Number: " + roomNumber);
                        System.out.println("Check-in Date: " + checkinDate);
                        System.out.println("Check-out Date: " + checkoutDate);
                        System.out.println("Status: " + status);

                        System.out.print("\nDo you want to proceed with checkout? (yes/no): ");
                        String confirmation = sc.nextLine().trim().toLowerCase();

                        if (confirmation.equals("yes")) {

                            String updateReservationQuery = "UPDATE reservations SET status = 'COMPLETED', checkout_date = NOW() WHERE id = ?";
                            try (PreparedStatement updateReservationStmt = conn.prepareStatement(updateReservationQuery)) {
                                updateReservationStmt.setInt(1, id);
                                int rowsUpdated = updateReservationStmt.executeUpdate();

                                if (rowsUpdated > 0) {
                                    System.out.println("\nReservation marked as completed!");

                                    String updateCheckinQuery = "UPDATE checkins SET status = 'COMPLETED' WHERE id = ?";
                                    try (PreparedStatement updateCheckinStmt = conn.prepareStatement(updateCheckinQuery)) {
                                        updateCheckinStmt.setInt(1, id);
                                        int checkinRowsUpdated = updateCheckinStmt.executeUpdate();

                                        if (checkinRowsUpdated > 0) {
                                            System.out.println("Check-in status updated to COMPLETED.");
                                        } else {
                                            System.out.println("Warning: No check-in records were updated.");
                                        }
                                    }

                                    String updateRoomQuery = "UPDATE registerroom SET roomStatus = 'AVAILABLE' WHERE roomNumber = ?";
                                    try (PreparedStatement updateRoomStmt = conn.prepareStatement(updateRoomQuery)) {
                                        updateRoomStmt.setInt(1, roomNumber);
                                        int roomRowsUpdated = updateRoomStmt.executeUpdate();

                                        if (roomRowsUpdated > 0) {
                                            System.out.println("Room status updated to AVAILABLE.");
                                            conn.commit();
                                        } else {
                                            System.out.println("Warning: Room status update failed.");
                                            conn.rollback();
                                        }
                                    }
                                } else {
                                    System.out.println("\nError: Could not update the reservation.");
                                    conn.rollback();
                                }
                            }
                        } else {
                            System.out.println("\nOperation canceled. The reservation remains active.");
                        }
                    } else {
                        System.out.println("\nNo active reservations found for this room number.");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("\nError during checkout: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.out.println("Error during rollback: " + ex.getMessage());
                }
            }
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("Error closing the connection: " + e.getMessage());
                }
            }
        }
    }
}