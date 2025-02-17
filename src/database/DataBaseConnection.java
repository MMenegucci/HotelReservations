package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/projecthotel";
    private static final String USUARIO = "root";
    private static final String SENHA = "superhomen12";

    public static Connection conect() {
        try {
            return DriverManager.getConnection(URL, USUARIO, SENHA);
    } catch (SQLException e) {
        throw new RuntimeException("Error connecting to MySQL.", e);
        }
    }


    public static void main(String[] args) {
        Connection conexao = conect();
        if (conexao != null) {
            System.out.println("Connecting successfully!");
        }
    }
}
