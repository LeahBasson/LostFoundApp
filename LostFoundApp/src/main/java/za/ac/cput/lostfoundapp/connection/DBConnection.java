package za.ac.cput.lostfoundapp.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static Connection derbyConnection() throws SQLException {
        String dbURL = "jdbc:derby://localhost:1527/University";
        String username = "app";  // FIXED - was administrator
        String password = "app";  // FIXED - was admin

        System.out.println("About to get a connection....");
        Connection connection = DriverManager.getConnection(dbURL, username, password);
        System.out.println("Connection Established Successfully....");
        return connection;
    }
}