package com.ru.mag.db.jdbc.queries;

import com.ru.mag.db.jdbc.models.Client;
import com.ru.mag.db.jdbc.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientQueries {
    private static final String INSERT_PERSON =
            "INSERT INTO Person(first_name,last_name,email,phone_number) VALUES (?,?,?,?)";

    private static final String INSERT_CLIENT =
            "INSERT INTO Client(person_id,budget,area_interested_in) VALUES (?,?,?)";

    private static final String SELECT_ALL =
            "SELECT p.person_id, p.first_name, p.last_name, p.email, p.phone_number, c.budget, c.area_interested_in " +
                    "FROM Person p JOIN Client c ON p.person_id = c.person_id";

    private static final String SELECT_BY_ID = SELECT_ALL + " WHERE p.person_id=?";

    public void createClient(Client c) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();

        PreparedStatement ps = conn.prepareStatement(INSERT_PERSON, new String[]{"person_id"});
        ps.setString(1, c.getFirstName());
        ps.setString(2, c.getLastName());
        ps.setString(3, c.getEmail());
        ps.setString(4, c.getPhoneNumber());
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            int id = rs.getInt(1);
            c.setId(id);

            PreparedStatement ps2 = conn.prepareStatement(INSERT_CLIENT);
            ps2.setInt(1, id);
            ps2.setDouble(2, c.getBudget());
            ps2.setString(3, c.getAreaInterestedIn());
            ps2.executeUpdate();
        }
    }

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

    public ResultSet getAllClients() throws SQLException {
        return DatabaseConnection.getConnection()
                .prepareStatement(SELECT_ALL)
                .executeQuery();
    }
}
