package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Class for establishing database connection.*/
public class DBConnection {
    //JDBC URL parts
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//wgudb.ucertify.com:3306/";
    private static final String dbName = "WJ08smj";

    //JDBC URL
    private static final String jdbcURL = protocol + vendorName + ipAddress + dbName;

    //Driver and connection interface reference
    private static final String MYSQLJDBCDriver = "com.mysql.cj.jdbc.Driver"; //or com.mysql.jdbc.Driver
    private static Connection conn = null;

    //Login
    private static final String username = "U08smj";
    private static final String password = "53689383133";

    public static Connection startConnection() {
        try {
            Class.forName(MYSQLJDBCDriver);
            conn = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connection successful!");
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static Connection getConnection() {
        return conn;
    }

    public static Connection closeConnection() {
        try {
            conn.close();
            System.out.println("Connection closed.");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
