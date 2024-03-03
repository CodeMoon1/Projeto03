package br.com.souza.jdbcgeneric;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connectionfactory {
    private static Connection connection;

    private Connectionfactory(Connection connection) {

    }

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = initConnection();
        } else if (connection != null && connection.isClosed()) {
            connection = initConnection();
        }
        return connection;
    }

    private static Connection initConnection() {
        try {
            return DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/LOJA", "postgres", "820197");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
