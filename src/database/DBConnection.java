package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // ── Connection settings ─────────────────────────────────────────────────
    private static final String URL      = "jdbc:mysql://localhost:3306/farm_database";
    private static final String USER     = "root";       
    private static final String PASSWORD = "67890";      

    // Single shared instance (Singleton)
    private static Connection connection = null;

    // Private constructor 
    private DBConnection() {}

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                // Load the JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("[DB] Connected to MySQL successfully.");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("[DB] JDBC Driver not found. Add mysql-connector-j.jar to classpath.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("[DB] Connection failed: " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * It closes the connection when the application exits.
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("[DB] Connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("[DB] Error closing connection: " + e.getMessage());
        }
    }
}
