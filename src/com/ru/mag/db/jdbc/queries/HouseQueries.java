package com.ru.mag.db.jdbc.queries;

import com.ru.mag.db.jdbc.models.House;
import com.ru.mag.db.jdbc.util.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HouseQueries {
    private static final String INSERT =
            "INSERT INTO House(property_id, number_of_floors, garden_size_m2, number_of_bathrooms, number_of_rooms) " +
                    "VALUES (?,?,?,?,?)";

    private static final String SELECT_ALL =
            "SELECT p.property_id, p.price, p.square_meters, h.number_of_floors, h.garden_size_m2, h.number_of_bathrooms, h.number_of_rooms " +
                    "FROM Property p JOIN House h ON p.property_id = h.property_id";

    private static final String DELETE =
            "DELETE FROM House WHERE property_id=?";

    public int createHouse(House h) throws SQLException {
        PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(INSERT);
        ps.setInt(1, h.getPropertyId());
        ps.setInt(2, h.getNumberOfFloors());
        ps.setInt(3, h.getGardenSize());
        ps.setInt(4, h.getBathrooms());
        ps.setInt(5, h.getRooms());
        return ps.executeUpdate();
    }

    public ResultSet getAllHouses() throws SQLException {
        return DatabaseConnection.getConnection()
                .prepareStatement(SELECT_ALL)
                .executeQuery();
    }

    public int deleteHouse(int propertyId) throws SQLException {
        PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(DELETE);
        ps.setInt(1, propertyId);
        return ps.executeUpdate();
    }
}
