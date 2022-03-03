package org.nntu.restapi.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;

@Component
public class SqlConnection {

    private Connection connection;

    public void init() {
        try {
            connection = DriverManager.getConnection("jdbc:h2:mem:");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
