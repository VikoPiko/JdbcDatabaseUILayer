package com.ru.mag.db.jdbc.queries;

import com.ru.mag.db.jdbc.util.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ComplexQueries {
    public ResultSet getPropertiesWithOwners() throws SQLException {
        String sql =
                "SELECT pr.property_id, pr.price, pr.property_type, p.first_name, p.last_name " +
                        "FROM Property pr " +
                        "JOIN Property_Owner po ON pr.property_id = po.property_id " +
                        "JOIN Person p ON po.person_id = p.person_id";

        return DatabaseConnection.getConnection()
                .prepareStatement(sql)
                .executeQuery();
    }

    // 2. Top agents by deal price
    public ResultSet getTopAgents(double minPrice) throws SQLException {
        String sql =
                "SELECT DISTINCT p.first_name, p.last_name, s.final_price " +
                        "FROM Successful_Deals s " +
                        "JOIN Agent a ON s.agent_id = a.person_id " +
                        "JOIN Person p ON a.person_id = p.person_id " +
                        "WHERE s.final_price > ?";

        PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
        ps.setDouble(1, minPrice);
        return ps.executeQuery();
    }

    // 3. Clients with preferences
    public ResultSet getClientsWithPreferences() throws SQLException {
        String sql =
                "SELECT p.first_name, p.last_name, c.budget, pref.preference_type " +
                        "FROM Client c " +
                        "JOIN Person p ON c.person_id = p.person_id " +
                        "JOIN Preferences pref ON c.person_id = pref.client_id";

        return DatabaseConnection.getConnection()
                .prepareStatement(sql)
                .executeQuery();
    }
}
