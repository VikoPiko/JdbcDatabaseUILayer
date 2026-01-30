package com.ru.mag.db.jdbc.queries;

import com.ru.mag.db.jdbc.util.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PropertyImageQueries {
    private static final String INSERT =
            "INSERT INTO Property_Images(listing_id, image_data, image_url) VALUES (?,?,?)";

    private static final String SELECT_ALL =
            "SELECT image_id, listing_id, image_url FROM Property_Images";

    public int insertImage(int listingId, byte[] imageData, String url) throws SQLException {
        PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(INSERT);
        ps.setInt(1, listingId);
        ps.setBytes(2, imageData);
        ps.setString(3, url);
        return ps.executeUpdate();
    }

    public ResultSet getAllImages() throws SQLException {
        return DatabaseConnection.getConnection()
                .prepareStatement(SELECT_ALL)
                .executeQuery();
    }
}
