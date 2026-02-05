package com.ru.mag.db.jdbc.util;

import com.ru.mag.db.jdbc.models.Location;
import com.ru.mag.db.jdbc.models.Person;
import oracle.jdbc.proxy.annotation.Pre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBUtil {

    // -----------------------------
    // Cached PreparedStatements -> Not the best from what i read
    // -----------------------------
    private PreparedStatement createCompany = null;

    // -----------------------------
    // SQL Queries
    // -----------------------------
    // AGENT, Apartment, Garage, House, Listing, Person, Person_Roles, Preferences, Properties, Property_Image, Roles, Successful_Deals, Property_Owner

    private static final String INSERT_COMPANY_QUERY =
            "INSERT INTO Company(company_id, name, eik, address, phone) " +
                    "VALUES(?, ?, ?, address_t(?, ?, ?, ?), ?)";


    private static final String SELECT_ALL_APARTMENTS_QUERY =
            "Select * From Apartment";

    private static final String SELECT_APARTMENT_BY_ID_QUERY =
            "Select * From Apartment where property_id = ?";

    private static final String UPDATE_APARTMENT_QUERY =
            "Update Apartment Set NUMBER_OF_BATHROOMS = ?, NUMBER_OF_ROOMS = ? where property_id = ?";

    private static final String DELETE_APARTMENT_BY_ID_QUERY =
            "Delete * From Apartment where property_id = ?";

    private static final String SELECT_APARTMENT_BY_NUMBER_OF_ROOMS =
            "Select * From Apartments where NUMBER_OF_ROOMS >= ?";

    private static final String SELECT_ALL_PEOPLE_QUERY =
            "SELECT * FROM PERSON";
    private static final String SELECT_PERSON_BY_ID =
            "SELECT * FROM PERSON where id = ?";

    private static final String INSERT_PERSON_QUERY =
            "INSERT INTO PERSON(first_name, last_name, email, address, phone_number) values (?,?,?,?)";

    private static final String SELECT_AGENT_QUERY =
            "SELECT * FROM AGENT";

    private static final String SELECT_CLIENTS_QUERY =
            "SELECT * FROM CLIENT";

    public static final String SELECT_AGENT_BY_ID_QUERY =
            "SELECT * FROM Agent where person_id = ?";

    public static final String SELECT_CLIENT_BY_ID_QUERY =
            "SELECT * FROM Client where person_id = ?";

    public static final String SELECT_PROPERTIES_BY_OWNER_ID_QUERY =
            "SELECT * FROM Property where owner_id = ?";

    public static final String INSERT_AGENT_QUERY =
            "INSERT INTO Agent(salary, hireDate) VALUES(?,?)";

    public static final String UPDATE_AGENT_SALARY_QUERY =
            "UPDATE Agent Set salary = ? where person_id = ?";

    public static final String DELETE_AGENT_QUERY =
            "DELETE FROM Agent WHERE person_id = ?";



    // Current price where now is within [valid_from, valid_to] OR valid_to is NULL
    private static final String SELECT_CURRENT_PRICE_QUERY =
            "SELECT pp.price_id, pp.product_id, pp.valid_from, pp.valid_to, pp.price " +
                    "FROM Product_Price pp " +
                    "WHERE pp.product_id = ? " +
                    "  AND pp.valid_from <= SYSDATE " +
                    "  AND (pp.valid_to IS NULL OR pp.valid_to >= SYSDATE) " +
                    "ORDER BY pp.valid_from DESC";

    // INVENTORY: low stock
    private static final String SELECT_LOW_STOCK_BATCHES_QUERY =
            "SELECT b.batch_id, b.product_id, b.batch_no, b.expiry_date, i.qty, i.min_qty " +
                    "FROM Inventory i JOIN Batch b ON b.batch_id = i.batch_id " +
                    "WHERE i.qty < i.min_qty " +
                    "ORDER BY (i.min_qty - i.qty) DESC";

    // DELIVERY
    private static final String INSERT_DELIVERY_QUERY =
            "INSERT INTO Delivery(delivery_id, customer_id, doc_no, delivered_at, vehicle_plate, carrier, note) " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?)";

    private static final String INSERT_DELIVERY_PRODUCT_QUERY =
            "INSERT INTO Delivery_Product(delivery_product_id, delivery_id, product_id, batch_id, qty, unit_price) " +
                    "VALUES(?, ?, ?, ?, ?, ?)";

    private static final String SELECT_DELIVERY_HEADER_QUERY =
            "SELECT d.delivery_id, d.customer_id, d.doc_no, d.delivered_at, d.vehicle_plate, d.carrier, d.note " +
                    "FROM Delivery d WHERE d.delivery_id = ?";

    private static final String SELECT_DELIVERY_LINES_QUERY =
            "SELECT dp.delivery_product_id, dp.delivery_id, dp.product_id, p.name AS product_name, " +
                    "       dp.batch_id, b.batch_no, dp.qty, dp.unit_price " +
                    "FROM Delivery_Product dp " +
                    "JOIN Product p ON p.product_id = dp.product_id " +
                    "JOIN Batch b ON b.batch_id = dp.batch_id " +
                    "WHERE dp.delivery_id = ? " +
                    "ORDER BY dp.delivery_product_id";

    // -----------------------------
    // Connection handling
    // -----------------------------
    private Connection cachedConnection = null;

    private static final DBUtil instance = new DBUtil();

    private DBUtil() {}

    public static DBUtil getInstance() {
        return instance;
    }

    public Connection getConnection() {
        try {
            if (cachedConnection == null ||
                    cachedConnection.isClosed() ||
                    !cachedConnection.isValid(10)) {

                System.out.println("Attempting to get a new connection to DB!");
                //DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
                cachedConnection = DriverManager.getConnection(
                        "jdbc:oracle:thin:@localhost:1521:xe",
                        "coursework",
                        "123456"
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this.cachedConnection;
    }

    // -----------------------------
    // PreparedStatement getters
    // Looked some stuff up, apparently caching the req/res is not ideal -> Could lead to leaks & Open connecitons
    // Try with resources seems to be a better solution for this case.
    // -----------------------------
    // -----------------------------
    // CRUD METHODS: AGENTS
    // -----------------------------

    /**
     * Never cache PreparedStatement fields
     * Always:
     * create it
     * use it
     * close it (via try-with-resources)
     *
     * BETTER PRACTICE!!
     * public List<Agent> getAllAgents() {
     *     List<Agent> agents = new ArrayList<>();
     *
     *     try (PreparedStatement stmt =
     *              getConnection().prepareStatement(SELECT_AGENT_QUERY);
     *          ResultSet rs = stmt.executeQuery()) {
     *
     *         while (rs.next()) {
     *             agents.add(new Agent(
     *                 rs.getInt("person_id"),
     *                 rs.getDouble("salary"),
     *                 rs.getDate("hireDate")
     *             ));
     *         }
     *     } catch (SQLException e) {
     *         e.printStackTrace();
     *     }
     *
     *     return agents;
     * }
     * */


    public ResultSet getAllPeopleCommand() throws SQLException {
        try{
            PreparedStatement statement = getConnection().prepareStatement(SELECT_ALL_PEOPLE_QUERY);
            return statement.executeQuery();
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<Person> getShortenedPeopleCommand() throws SQLException {
        List<Person> list = new ArrayList<>();

        PreparedStatement statement = getConnection().prepareStatement(
                "SELECT person_id, first_name, last_name FROM person"
        );

        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            list.add(new Person(
                    rs.getInt("person_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name")
            ));
        }

        return list;
    }

    public ResultSet getPersonById(int id) throws SQLException {
        String sql = "SELECT * FROM Person WHERE person_id=?";
        PreparedStatement ps = getConnection().prepareStatement(sql);
        ps.setInt(1, id);
        return ps.executeQuery();
    }

    public ResultSet updatePersonById(){
        try{
            PreparedStatement statement = getConnection().prepareStatement(SELECT_ALL_PEOPLE_QUERY);
            return statement.executeQuery();
        } catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet deletePersonById(){
        try{
            PreparedStatement statement = getConnection().prepareStatement(SELECT_ALL_PEOPLE_QUERY);
            return statement.executeQuery();
        } catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }
    public void insertPerson(String fn, String ln, String email, String phone) throws SQLException {
        String sql = "INSERT INTO Person(first_name,last_name,email,phone_number) VALUES (?,?,?,?)";
        PreparedStatement ps = getConnection().prepareStatement(sql);
        ps.setString(1, fn);
        ps.setString(2, ln);
        ps.setString(3, email);
        ps.setString(4, phone);
        ps.executeUpdate();
    }

    public void insertClient(int personId, double budget, String areaInterestedIn) throws SQLException {
        String sql = "INSERT INTO Client(person_id, budget, area_interested_in) VALUES (?,?,?)";
        PreparedStatement ps = getConnection().prepareStatement(sql);
        ps.setInt(1, personId);
        ps.setDouble(2, budget);
        ps.setString(3, areaInterestedIn);
        ps.executeUpdate();
    }

    public void updateClient(int id, double budget, String area_interested_in) throws SQLException {
        String sql = "UPDATE Client SET budget=?, area_interested_in=? WHERE person_id=?";
        PreparedStatement ps = getConnection().prepareStatement(sql);
        ps.setDouble(1, budget);
        ps.setString(2, area_interested_in);
        ps.setInt(3, id);
        ps.executeUpdate();
    }


    public void updatePerson(int id, String fn, String ln, String email, String phone) throws SQLException {
        String sql = "UPDATE Person SET first_name=?, last_name=?, email=?, phone_number=? WHERE person_id=?";
        PreparedStatement ps = getConnection().prepareStatement(sql);
        ps.setString(1, fn);
        ps.setString(2, ln);
        ps.setString(3, email);
        ps.setString(4, phone);
        ps.setInt(5, id);
        ps.executeUpdate();
    }

    public void deletePerson(int id) throws SQLException {
        String sql = "DELETE FROM Person WHERE person_id=?";
        PreparedStatement ps = getConnection().prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }
    public void deleteClient(int id) throws SQLException {
        String sql = "DELETE FROM Client WHERE person_id=?";
        PreparedStatement ps = getConnection().prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    public ResultSet getAllClientsCommand(){
        try{
            PreparedStatement statement = getConnection().prepareStatement(SELECT_CLIENTS_QUERY);
            return statement.executeQuery();
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet getClientByIdCommand(int id){
        try{
            PreparedStatement statement = getConnection().prepareStatement(SELECT_CLIENT_BY_ID_QUERY);
            statement.setInt(1, id);

            return statement.executeQuery();
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet getPropertyByOwnerId(int id){
        try{
            PreparedStatement statement = getConnection().prepareStatement(SELECT_PROPERTIES_BY_OWNER_ID_QUERY);
            statement.setInt(1, id);

            return statement.executeQuery();
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private String INSERT_PROPERTY_QUERY =
            "Insert into Property (price, square_meters, location, property_type, owner_id) " +
                    "values (?, ?, ?, ?, ?)";

    public int insertProperty(Double price,
                              double square_meters,
                              Location location,
                              String property_type,
                              int owner_id) throws SQLException {

        String sql = "INSERT INTO Property (price, square_meters, location, property_type, owner_id) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, price);
            ps.setDouble(2, square_meters);

            // 👇 convert Location -> Oracle STRUCT
            Object[] attrs = new Object[]{
                    location.getLatitude(),
                    location.getLongitude(),
                    location.getCity()
            };

            Struct locationStruct = conn.createStruct("LOCATION_T", attrs);

// 👇 THIS is the correct JDBC call
            ps.setObject(3, locationStruct);

            ps.setString(4, property_type);
            ps.setInt(5, owner_id);

            return ps.executeUpdate();
        }
    }

    //Okay not the best either return a list or just use the old one...
    //Apparently ResultSet is out of scope by the time its returned.

}