package cscc43.assignment.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import cscc43.assignment.throwable.DatabaseException;

public class Database {
    private static final String HOSTNAME = "localhost";
    private static final String SCHEMA = "HL";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static Connection connection = null;
    private static int nestedConnect = 0;

    public static Connection connect() {
        nestedConnect++;
        if (connection != null) {
            return connection;
        }
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(getConnectionString());
            return connection;
        } catch (ClassNotFoundException err) {
            err.printStackTrace();
            throw new DatabaseException(err.getMessage());
        } catch (SQLException err) {
            err.printStackTrace();
            throw new DatabaseException(err.getMessage());
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void disconnect() {
        nestedConnect--;
        if (nestedConnect > 0) {
            return;
        }
        try {
            if (connection != null) {
                connection.close();
                connection = null;
            }
        } catch (SQLException err) {
            err.printStackTrace();
            throw new DatabaseException(err.getMessage());
        }
    }

    public static void commit() {
        try {
            connection.commit();
        } catch (SQLException err) {
            err.printStackTrace();
            throw new RuntimeException(err);
        }
    }

    public static void rollback() {
        try {
            connection.rollback();
        } catch (SQLException err) {
            err.printStackTrace();
            throw new RuntimeException(err);
        }
    }

    private static String getConnectionString() {
        return String.format(
            "jdbc:mysql://%s/%s?user=%s&password=%s",
            HOSTNAME,
            SCHEMA,
            USER,
            PASSWORD
        );
    }
}
