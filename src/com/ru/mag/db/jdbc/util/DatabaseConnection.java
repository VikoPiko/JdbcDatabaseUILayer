package com.ru.mag.db.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    //cached connection
    private static Connection connection;
    //default constructor
    private DatabaseConnection(){}

    private static final DatabaseConnection instance = new DatabaseConnection();

    public static DatabaseConnection getInstance() {
        return instance;
    }

    //func to get/set connection
    public static Connection getConnection(){
        try{
            if(connection == null || connection.isClosed() || !connection.isValid(10)){
                DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
                System.out.println("Attempting to get a new connection to DB!");
                connection = DriverManager.getConnection(
                        "jdbc:oracle:thin:@localhost:1521:xe",
                        "coursework",
                        "123456"
                );
            }
            return connection;
        }catch(SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to get connection", e);
        }
    }
}
