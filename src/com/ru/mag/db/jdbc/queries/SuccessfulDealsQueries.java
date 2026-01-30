package com.ru.mag.db.jdbc.queries;

import com.ru.mag.db.jdbc.util.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SuccessfulDealsQueries {
    private static final String INSERT =
            "INSERT INTO Successful_Deals(property_id, final_price, agent_id, client_id) VALUES (?,?,?,?)";

    private static final String SELECT_ALL =
            "SELECT sd.deal_id, p.first_name AS agent, c.first_name AS client, sd.final_price " +
                    "FROM Successful_Deals sd " +
                    "JOIN Person p ON sd.agent_id = p.person_id " +
                    "JOIN Person c ON sd.client_id = c.person_id";

    public int createDeal(int propertyId, double price, int agentId, int clientId) throws SQLException {
        PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(INSERT);
        ps.setInt(1, propertyId);
        ps.setDouble(2, price);
        ps.setInt(3, agentId);
        ps.setInt(4, clientId);
        return ps.executeUpdate();
    }

    public ResultSet getAllDeals() throws SQLException {
        return DatabaseConnection.getConnection()
                .prepareStatement(SELECT_ALL)
                .executeQuery();
    }
}
