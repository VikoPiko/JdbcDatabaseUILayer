package com.ru.mag.db.jdbc.queries;

import com.ru.mag.db.jdbc.util.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GarageQueries {
    private static final String INSERT =
            "INSERT INTO Garage(property_id) VALUES (?)";

    private static final String SELECT_ALL =
            "SELECT p.property_id, p.price " +
                    "FROM Property p JOIN Garage g ON p.property_id = g.property_id";

    public int createGarage(int propertyId) throws SQLException {
        PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(INSERT);
        ps.setInt(1, propertyId);
        return ps.executeUpdate();
    }

    public int deleteGarage(int propertyId) throws SQLException {
        try{
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("DELETE FROM Garage where property_id = ?");
            ps.setInt(1, propertyId);
            return ps.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
            return 0;
        }
    }

    public ResultSet getAllGarages() throws SQLException {
        return DatabaseConnection.getConnection()
                .prepareStatement(SELECT_ALL)
                .executeQuery();
    }
}
