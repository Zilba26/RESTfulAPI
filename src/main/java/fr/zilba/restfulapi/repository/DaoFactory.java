package fr.zilba.restfulapi.repository;

import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

@Repository
public class DaoFactory {

    public static final ResourceBundle CONFIGURATION = ResourceBundle.getBundle("config");

    public Connection getConnection() throws SQLException {
        String serverName = CONFIGURATION.getString("SERVER_NAME");
        String port = CONFIGURATION.getString("SERVER_PORT");
        String dbName = CONFIGURATION.getString("DATABASE_NAME");
        String username = CONFIGURATION.getString("USERNAME");
        String password = CONFIGURATION.getString("PASSWORD");

        return DriverManager.getConnection("jdbc:mysql://" + serverName + ":" + port + "/" + dbName, username, password);
    }

}