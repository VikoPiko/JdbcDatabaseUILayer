package com.ru.mag.db.jdbc.queries;

import com.ru.mag.db.jdbc.util.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RolesQueries {
    private static final String INSERT =
            "INSERT INTO Roles(role_type) VALUES (?)";

    private static final String SELECT_ALL =
            "SELECT role_id, role_type FROM Roles";

    public int createRole(String roleType) throws SQLException {
        PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(INSERT);
        ps.setString(1, roleType);
        return ps.executeUpdate();
    }

    public ResultSet getAllRoles() throws SQLException {
        return DatabaseConnection.getConnection()
                .prepareStatement(SELECT_ALL)
                .executeQuery();
    }
}
