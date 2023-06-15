package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class database {
    public static String url = "jdbc:oracle:thin:@localhost:1521/qlchtt_orcl";
    public static String username = "QLCHTT";
    public static String password = "PASSWORD";

    public static Connection connection;

    public static void connect() {
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection successful");
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }

    public static Connection getConnection() {
        if (connection == null) {
            connect();
        }
        return connection;
    }

    // ngat ket noi
    public static void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Connection closed");
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
}
