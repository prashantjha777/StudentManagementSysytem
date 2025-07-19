package StudentManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/student_management_system";
    private static final String USER = "root";           // Replace with your MySQL username
    private static final String PASSWORD = "your_pass";  // Replace with your MySQL password

    private static Connection conn = null;

    public static Connection getConnection() {
        if (conn == null) {
            try {
                // Load MySQL JDBC Driver (optional in newer versions)
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Database connected successfully.");
            } catch (ClassNotFoundException e) {
                System.out.println("JDBC Driver not found: " + e.getMessage());
            } catch (SQLException e) {
                System.out.println("Connection failed: " + e.getMessage());
            }
        }
        return conn;
    }

    public static void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.out.println("Failed to close connection: " + e.getMessage());
        }
    }
}