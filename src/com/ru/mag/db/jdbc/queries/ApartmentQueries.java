package com.ru.mag.db.jdbc.queries;

import com.ru.mag.db.jdbc.util.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ApartmentQueries {

    public static String GET_APARTMENTS_QUERY =
            "Select * from Apartment";

    public ResultSet getApartments() throws SQLException{
        try{
            PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(GET_APARTMENTS_QUERY);
            return statement.executeQuery();
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static String GET_APARTMENT_BY_ID_QUERY =
            "Select * from Apartment where property_id = ?";

    public ResultSet getApartmentById(int id) throws SQLException{
        try{
            PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(GET_APARTMENT_BY_ID_QUERY);
            statement.setInt(1, id);
            return statement.executeQuery();
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private static final String INSERT =
            "INSERT INTO Apartment(property_id, floor, number_of_bathrooms, number_of_rooms) VALUES (?,?,?,?)";
    public int createApartment(int propertyId, int floor, int baths, int rooms) throws SQLException {
        PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(INSERT);
        ps.setInt(1, propertyId);
        ps.setInt(2, floor);
        ps.setInt(3, baths);
        ps.setInt(4, rooms);
        return ps.executeUpdate();
    }

    public static String UPDATE_APARTMENT_QUERY =
            "Update Apartment set price = ? where property_id = ?";

    public int updateApartment(double price) throws SQLException{
        try{
            PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(UPDATE_APARTMENT_QUERY);
            statement.setDouble(1, price);
            return statement.executeUpdate();
        } catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public static String DELETE_APARTMENT_QUERY =
            "Delete from Apartment where property_id = ?";

    public int deleteApartment(int id) throws SQLException{
        try{
            PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(DELETE_APARTMENT_QUERY);
            statement.setInt(1, id);
            return statement.executeUpdate();
        } catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }
}
