package com.ru.mag.db.jdbc.queries;

import com.ru.mag.db.jdbc.util.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PropertyOwnerQueries {
    private static final String INSERT =
            "INSERT INTO Property_Owner(person_id, property_id) VALUES (?,?)";

    private static final String SELECT_ALL =
            "SELECT p.first_name, p.last_name, pr.property_id, pr.price " +
                    "FROM Property_Owner po " +
                    "JOIN Person p ON po.person_id = p.person_id " +
                    "JOIN Property pr ON po.property_id = pr.property_id";

    public int assignOwner(int personId, int propertyId) throws SQLException {
        PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(INSERT);
        ps.setInt(1, personId);
        ps.setInt(2, propertyId);
        return ps.executeUpdate();
    }

    public ResultSet getAllPropertyOwners() throws SQLException {
        return DatabaseConnection.getConnection()
                .prepareStatement(SELECT_ALL)
                .executeQuery();
    }
}
