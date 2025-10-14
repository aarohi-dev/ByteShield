package com.byteshield.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DatabaseConnection class handles PostgreSQL database connectivity
 * for the ByteShield cybersecurity training application.
 */
public class DatabaseConnection {
    
    // Database connection constants
    private static final String DB_HOST = "localhost";
    private static final String DB_PORT = "5432";
    private static final String DB_NAME = "byteshield_db";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "admin";
    
    // JDBC URL
    private static final String JDBC_URL = String.format(
        "jdbc:postgresql://%s:%s/%s", 
        DB_HOST, DB_PORT, DB_NAME
    );
    
    /**
     * Establishes a connection to the PostgreSQL database.
     * 
     * @return Connection object for database operations
     * @throws SQLException if database connection fails
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Load PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");
            
            // Establish connection
            Connection connection = DriverManager.getConnection(
                JDBC_URL, 
                DB_USER, 
                DB_PASSWORD
            );
            
            System.out.println("✅ Successfully connected to PostgreSQL database: " + DB_NAME);
            return connection;
            
        } catch (ClassNotFoundException e) {
            System.err.println("❌ PostgreSQL JDBC driver not found: " + e.getMessage());
            throw new SQLException("PostgreSQL JDBC driver not found", e);
        } catch (SQLException e) {
            System.err.println("❌ Failed to connect to PostgreSQL database:");
            System.err.println("   Host: " + DB_HOST + ":" + DB_PORT);
            System.err.println("   Database: " + DB_NAME);
            System.err.println("   User: " + DB_USER);
            System.err.println("   Error: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Tests the database connection and prints status.
     * 
     * @return true if connection is successful, false otherwise
     */
    public static boolean testConnection() {
        try (Connection connection = getConnection()) {
            if (connection != null && !connection.isClosed()) {
                System.out.println("✅ Database connection test successful!");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("❌ Database connection test failed: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Gets database connection details for display purposes.
     * 
     * @return String containing connection details
     */
    public static String getConnectionInfo() {
        return String.format(
            "Database: %s\nHost: %s:%s\nUser: %s", 
            DB_NAME, DB_HOST, DB_PORT, DB_USER
        );
    }
}
