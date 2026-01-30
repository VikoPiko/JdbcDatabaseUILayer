package com.ru.mag.db.jdbc.queries;

import com.ru.mag.db.jdbc.util.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ListingQueries {
    private static final String INSERT_LISTING =
            "INSERT INTO Listing(type_of_listing, description, notes) VALUES (?,?,?)";

    private static final String SELECT_ALL_LISTING =
            "SELECT * FROM Listing";

    public int createListing(String type, String desc, String notes) throws SQLException {
        PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(INSERT_LISTING);
        ps.setString(1, type);
        ps.setString(2, desc);
        ps.setString(3, notes);
        return ps.executeUpdate();
    }

    public static final String UPDATE_LISTING =
            "Update Listing set type = ?, desc = ?, notes = ? where listing_id = ?";

    public int updateListing(int listingId, String type, String desc, String notes){
        try{
            PreparedStatement statement =  DatabaseConnection.getConnection().prepareStatement(UPDATE_LISTING);
            statement.setString(1, type);
            statement.setString(2, desc);
            statement.setString(3, notes);
            statement.setInt(4, listingId);

            return statement.executeUpdate();
        } catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public ResultSet getAllListings() throws SQLException {
        return DatabaseConnection.getConnection()
                .prepareStatement(SELECT_ALL_LISTING)
                .executeQuery();
    }
}
