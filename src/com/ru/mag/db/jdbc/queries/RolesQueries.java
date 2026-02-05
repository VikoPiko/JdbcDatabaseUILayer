package com.ru.mag.db.jdbc.queries;

import com.ru.mag.db.jdbc.util.DatabaseConnection;
import oracle.sql.STRUCT;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RolesQueries {

    private static final String INSERT =
            "INSERT INTO Roles(role_type, role_privilages) " +
                    "VALUES (?, role_privilages_t(?,?,?))";

    private static final String SELECT_ALL =
            "SELECT role_id, role_type, role_privilages FROM Roles";

    private static final String DELETE =
            "DELETE FROM Roles WHERE role_id = ?";

    public int createRole(String roleType, int full, int post, int auth) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(INSERT);
        ps.setString(1, roleType);
        ps.setInt(2, full);
        ps.setInt(3, post);
        ps.setInt(4, auth);
        return ps.executeUpdate();
    }

    public ResultSet getAllRoles() throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(SELECT_ALL);
        return ps.executeQuery();
    }

    public int deleteRole(int id) throws SQLException {
        PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(DELETE);
        ps.setInt(1, id);
        return ps.executeUpdate();
    }

    /**
     * Utility: convert STRUCT (role_privilages_t) to int array [has_full_access, can_post, can_authorize_sale]
     */
    public static int[] extractPrivileges(STRUCT struct) throws SQLException {
        if (struct == null) return new int[]{0,0,0};
        Object[] attrs = struct.getAttributes();
        int hasFullAccess = ((Number) attrs[0]).intValue();
        int canPost = ((Number) attrs[1]).intValue();
        int canAuthorizeSale = ((Number) attrs[2]).intValue();
        return new int[]{hasFullAccess, canPost, canAuthorizeSale};
    }
}
