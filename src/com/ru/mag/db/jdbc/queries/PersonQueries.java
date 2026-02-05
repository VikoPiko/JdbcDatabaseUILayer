package com.ru.mag.db.jdbc.queries;

import com.ru.mag.db.jdbc.util.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonQueries {

    public ResultSet getALlPeople() throws SQLException {
        try{
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM person");
            return ps.executeQuery();
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }


}
