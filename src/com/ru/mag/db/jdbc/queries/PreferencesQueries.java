package com.ru.mag.db.jdbc.queries;

import com.ru.mag.db.jdbc.util.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PreferencesQueries {
    private static final String INSERT =
            "INSERT INTO Preferences(client_id, preference_type) VALUES (?,?)";

    private static final String SELECT_ALL =
            "SELECT p.first_name, p.last_name, pref.preference_type " +
                    "FROM Preferences pref " +
                    "JOIN Person p ON pref.client_id = p.person_id";

    public int createPreference(int clientId, String type) throws SQLException {
        PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(INSERT);
        ps.setInt(1, clientId);
        ps.setString(2, type);
        return ps.executeUpdate();
    }

    public ResultSet getAllPreferences() throws SQLException {
        return DatabaseConnection.getConnection()
                .prepareStatement(SELECT_ALL)
                .executeQuery();
    }
}
