package functions;

import database.DataBaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class EditOrDelete {
    public static void editOrDelete(Scanner sc) {
        Connection conn = DataBaseConnection.conect();

        boolean running = true;

        while (running) {
            System.out.println("\n--- Edit or Delete Data ---");
            System.out.println("1. Edit Guest Data");
            System.out.println("2. Delete Guest Data");
            System.out.println("3. Edit Room Data");
            System.out.println("4. Delete Room Data");
            System.out.println("5. Exit");
            System.out.print("\nChoose an option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    editGuestData(sc);
                    break;
                case 2:
                    deleteGuestData(sc);
                    break;
                case 3:
                    editRoomData(sc);
                    break;
                case 4:
                    deleteRoomData(sc);
                    break;
                case 5:
                    System.out.println("Returning to main menu.");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    public static void editGuestData(Scanner sc) {
        Connection conn = DataBaseConnection.conect();

        if (conn == null) {
            System.out.println("Error: Database connection failed.");
            return;
        }

        try {
            System.out.print("Enter the CPF of the guest to edit: ");
            String cpf = sc.nextLine().trim();

            if (cpf.isEmpty()) {
                System.out.println("Error: The CPF cannot be empty.");
                return;
            }

            String checkQuery = "SELECT name, phone, email FROM registerguest WHERE cpf = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                checkStmt.setString(1, cpf);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("Error: Guest with CPF " + cpf + " not found.");
                        return;
                    } else {
                        String name = rs.getString("name");
                        String phone = rs.getString("phone");
                        String email = rs.getString("email");

                        System.out.println("\nCurrent Guest Data:");
                        System.out.println("Name: " + name + " | Phone: " + phone + " | Email: " + email);
                    }
                }
            }

            System.out.print("\nWhat do you want to change (name, phone, email)? ");
            String option = sc.nextLine().trim().toLowerCase();

            String query = "";
            String newValue = "";

            switch (option) {
                case "name":
                    System.out.print("Enter the new name: ");
                    newValue = sc.nextLine().trim();
                    query = "UPDATE registerguest SET name = ? WHERE cpf = ?";
                    break;
                case "phone":
                    System.out.print("Enter the new phone number: ");
                    newValue = sc.nextLine().trim();
                    query = "UPDATE registerguest SET phone = ? WHERE cpf = ?";
                    break;
                case "email":
                    System.out.print("Enter the new email: ");
                    newValue = sc.nextLine().trim();
                    query = "UPDATE registerguest SET email = ? WHERE cpf = ?";
                    break;
                default:
                    System.out.println("Invalid option.");
                    return;
            }

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, newValue);
                stmt.setString(2, cpf);

                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("\nGuest data updated successfully!");
                } else {
                    System.out.println("Error: Guest not found.");
                    return;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error updating guest data: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("Error closing the connection: " + e.getMessage());
                }
            }
        }
    }

    public static void deleteGuestData(Scanner sc) {
        Connection conn = DataBaseConnection.conect();

        if (conn == null) {
            System.out.println("Error: Database connection failed.");
            return;
        }

        try {
            System.out.print("Enter the CPF of the guest to delete: ");
            String cpf = sc.nextLine().trim();

            System.out.print("\nAre you sure you want to delete this guest? (yes/no): ");
            String confirmation = sc.nextLine().trim().toLowerCase();

            if (!confirmation.equals("yes")) {
                System.out.println("Operation cancelled.");
                return;
            }

            String query = "DELETE FROM registerguest WHERE cpf = ?";

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, cpf);

                int rowsDeleted = stmt.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("\nGuest deleted successfully!");
                } else {
                    System.out.println("Error: Guest not found.");
                    return;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error deleting guest: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("Error closing the connection: " + e.getMessage());
                }
            }
        }
    }

    public static void editRoomData(Scanner sc) {
        Connection conn = DataBaseConnection.conect();

        if (conn == null) {
            System.out.println("Error: Database connection failed.");
            return;
        }

        try {
            System.out.print("Enter the Room Number to edit: ");
            int roomNumber = sc.nextInt();
            sc.nextLine();

            String checkQuery = "SELECT id, roomNumber, type, pricePerNight FROM registerroom WHERE roomNumber = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                checkStmt.setInt(1, roomNumber);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("Error: Room with number " + roomNumber + " not found.");
                        return;
                    } else {

                        int id = rs.getInt("id");
                        roomNumber = rs.getInt("roomNumber");
                        String type = rs.getString("type");
                        double pricePerNight = rs.getDouble("pricePerNight");

                        System.out.println("\nCurrent Room Data:");
                        System.out.println("Room: " + roomNumber + " | Id: " + id + " | Type: " + type + " | Price per Night: " + pricePerNight);
                    }
                }
            }

            System.out.print("What do you want to change (type or price)? ");
            String option = sc.nextLine().trim().toLowerCase();

            String query = "";
            String newValue = "";

            switch (option) {
                case "type":
                    System.out.print("Enter the new type: ");
                    newValue = sc.nextLine().trim().toUpperCase();
                    query = "UPDATE registerroom SET type = ? WHERE roomNumber = ?";
                    break;
                case "price":
                    System.out.print("Enter the new price: ");
                    newValue = sc.nextLine().trim();
                    query = "UPDATE registerroom SET pricePerNight = ? WHERE roomNumber = ?";
                    break;
                default:
                    System.out.println("Invalid option.");
                    return;
            }

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, newValue);
                stmt.setInt(2, roomNumber);

                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("\nRoom data updated successfully!");
                } else {
                    System.out.println("Error: Room not found.");
                    return;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error updating room data: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("Error closing the connection: " + e.getMessage());
                }
            }
        }
    }

    public static void deleteRoomData(Scanner sc) {
        Connection conn = DataBaseConnection.conect();

        if (conn == null) {
            System.out.println("Error: Database connection failed.");
            return;
        }

        try {
            System.out.print("Enter the Room Number to delete: ");
            int roomNumber = sc.nextInt();
            sc.nextLine();

            System.out.print("Are you sure you want to delete this room? (yes/no): ");
            String confirmation = sc.nextLine().trim().toLowerCase();

            if (!confirmation.equals("yes")) {
                System.out.println("Operation cancelled.");
                return;
            }

            String query = "DELETE FROM registerroom WHERE roomNumber = ?";

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, roomNumber);

                int rowsDeleted = stmt.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("\nRoom deleted successfully!");
                } else {
                    System.out.println("Error: Room not found.");
                    return;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error deleting room: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("Error closing the connection: " + e.getMessage());
                }
            }
        }
    }
}