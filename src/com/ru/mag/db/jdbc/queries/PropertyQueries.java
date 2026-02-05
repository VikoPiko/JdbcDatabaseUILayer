package com.ru.mag.db.jdbc.queries;

import com.ru.mag.db.jdbc.models.Property;
import com.ru.mag.db.jdbc.util.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PropertyQueries {

    private static final String INSERT =
            "INSERT INTO Property(price,square_meters,property_type,owner_id) VALUES (?,?,?,?)";

    private static final String SELECT_ALL =
            "SELECT property_id, price, square_meters, property_type FROM Property";

    public int createProperty(Property p, int ownerId) throws SQLException {
        try{
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(INSERT);
            ps.setDouble(1, p.getPrice());
            ps.setInt(2, p.getSquareMeters());
            ps.setString(3, p.getType());
            ps.setInt(4, ownerId);
            return ps.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
            return 0;
        }
    }

    private static final String UPDATE =
            "Update Property set price=?, square_meters=?, property_type=? where property_id=?";

    public int updateProperty(Property p, int ownerId) throws SQLException {
        try{
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(UPDATE);
            ps.setDouble(1, p.getPrice());
            ps.setInt(2, p.getSquareMeters());
            ps.setString(3, p.getType());
            ps.setInt(4, ownerId);
            return ps.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
            return 0;
        }
    }

    private static final String DELETE =
            "Delete from Property where property_id=?";

    public int deleteProperty(Property p) throws SQLException {
        try{
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(DELETE);
            ps.setInt(1, p.getId());
            return ps.executeUpdate();
        }  catch(SQLException e){
            e.printStackTrace();
            return 0;
        }
    }

    public ResultSet getAllProperties() throws SQLException {
        return DatabaseConnection.getConnection()
                .prepareStatement(SELECT_ALL)
                .executeQuery();
    }

    private static final String GetPropByID =
            "Select * from Property where property_id=?";

    public ResultSet getPropertyById(int id) throws SQLException {
        try{
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(GetPropByID);
            ps.setInt(1, id);
            return ps.executeQuery();
        } catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

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
}
