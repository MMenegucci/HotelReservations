package functions;

import database.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class RoomsDataBase {

    public static void listAllRooms(Scanner sc){
        String query = "SELECT * FROM registerroom";

        try (Connection conn = DataBaseConnection.conect();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("Rooms in DataBase: ");
            while (rs.next()) {
                int id = rs.getInt("id");
                int roomNumber = rs.getInt("roomNumber");
                String type = rs.getString("type");
                double pricePerNight = rs.getDouble("pricePerNight");
                String roomStatus = rs.getString("roomStatus");

                System.out.println("ID: " + id + " | Quarto: " + roomNumber + " | Tipo: " + type +
                        " | Preço por Noite: R$" + pricePerNight + " | Status: " + roomStatus);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar quartos: " + e.getMessage());
        }
    }
}
