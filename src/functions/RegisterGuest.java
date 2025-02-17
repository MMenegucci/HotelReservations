package functions;

import database.DataBaseConnection;
import models.Guest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import static models.Guest.guests;

public class RegisterGuest {
    public static void registerGuest(Scanner sc) {

            System.out.print("Enter guest cpf: ");
            String cpf = sc.nextLine();

            for (Guest g : guests) {
                if (g.getCpf().equals(cpf)) {
                    System.out.println("Error: This CPF is already registered");
                }
            }

            System.out.print("Enter guest name: ");
            String name = sc.nextLine();

            System.out.print("Enter the guest's phone number: ");
            String phone = sc.nextLine();

            System.out.print("Enter the guest's email: ");
            String email = sc.nextLine();

            Guest guest = new Guest(cpf, name, phone, email);
            guests.add(guest);

            System.out.println();
            System.out.println("Guest successfully registered!");

        String sql = "INSERT INTO users (cpf, name, phone, email) VALUES (?, ?, ?, ?)";

        try (Connection conexao = DataBaseConnection.conect();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            stmt.setString(2, name);
            stmt.setString(3, phone);
            stmt.setString(4, email);
            stmt.executeUpdate();

            System.out.println("Guest successfully registered!");
        } catch (SQLException e) {
            System.out.println("Error registering guest. " + e.getMessage());
        }
    }
}

