package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class database {
	public static String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	public static String username = "QLCHTT";
	public static String password = "PASSWORD";

	
	public static Connection connection;
	public static void connecting() {
		try {
		    connection = DriverManager.getConnection(url, username, password);
		    System.out.println("Connection successful");
		    
		} catch (SQLException e) {
		    System.out.println("Connection failed: " + e.getMessage());
		}
	}
	
	public static Connection getConnection() {
		try {
		    connection = DriverManager.getConnection(url, username, password);
		    System.out.println("Connection successful");
		    
		} catch (SQLException e) {
		    System.out.println("Connection failed: " + e.getMessage());
		}
		return connection;
	}
    
	
	public static void SelectFromSanPham () {
		try {
			String query = "SELECT maSP, tenSP FROM SANPHAM";
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
	   
			while (resultSet.next()) {
				String maSP = resultSet.getString("maSP");
				String tenSP = resultSet.getString("tenSP");
				//String mauSac = resultSet.getString("mauSac"); // Use getString() instead of getInt()
				System.out.println("ID: " + maSP + ", Ten san pham: " + tenSP );
			}
		} catch (SQLException e) {
	    System.out.println("Query failed: " + e.getMessage());
		}
	}
	
// ngat ket noi
	public static void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
}
