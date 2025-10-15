package com.byteshield.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import io.github.cdimascio.dotenv.Dotenv;

/**
 * DatabaseConnection class handles PostgreSQL database connectivity
 * for the ByteShield cybersecurity training application.
 * 
 * Configuration is securely loaded from a .env file in the project root.
 */
public class DatabaseConnection {
    
    // Reusable connection object
    private static Connection connection = null;

    /**
     * Establishes a connection to the PostgreSQL database.
     * Reads configuration from the .env file.
     * 
     * @return Connection object for database operations
     * @throws SQLException if database connection fails
     */
    public static Connection getConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            return connection;
        }

        try {
            // Load environment variables from .env file
            Dotenv dotenv = Dotenv.load();

            String url = dotenv.get("DB_URL");
            String user = dotenv.get("DB_USER");
            String password = dotenv.get("DB_PASSWORD");

            // Load PostgreSQL driver
            Class.forName("org.postgresql.Driver");

            // Establish connection
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Successfully connected to PostgreSQL database using .env configuration");
            return connection;

        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL JDBC driver not found: " + e.getMessage());
            throw new SQLException("PostgreSQL JDBC driver not found", e);
        } catch (SQLException e) {
            System.err.println("Failed to connect to PostgreSQL database.");
            System.err.println("   Error: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("Error loading .env file or variables: " + e.getMessage());
            throw new SQLException("Environment configuration error", e);
        }
    }

    /**
     * Tests the database connection and prints status.
     * 
     * @return true if connection is successful, false otherwise
     */
    public static boolean testConnection() {
        try (Connection testConn = getConnection()) {
            if (testConn != null && !testConn.isClosed()) {
                System.out.println("Database connection test successful!");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Database connection test failed: " + e.getMessage());
        }
        return false;
    }

    /**
     * Displays connection info (non-sensitive).
     * 
     * @return String containing connection details
     */
    public static String getConnectionInfo() {
        try {
            Dotenv dotenv = Dotenv.load();
            String url = dotenv.get("DB_URL");
            String user = dotenv.get("DB_USER");
            
            // Extract database name from URL for display
            String dbName = "unknown";
            if (url != null && url.contains("/")) {
                dbName = url.substring(url.lastIndexOf("/") + 1);
            }
            
            return String.format(
                "Database: %s\nURL: %s\nUser: %s\n\nConfiguration loaded from .env file",
                dbName, url, user
            );
        } catch (Exception e) {
            return "Unable to load connection info from .env file";
        }
    }
}
