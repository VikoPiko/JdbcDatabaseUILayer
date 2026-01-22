package com.ru.mag.db.jdbc.util;

import java.sql.*;

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

    private static final String SELECT_AGENT_QUERY =
            "SELECT * FROM AGENT";

    public static final String SELECT_AGENT_BY_ID_QUERY =
            "SELECT * FROM Agent where person_id = ?";

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

    private Connection getConnection() {
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
    private PreparedStatement getCreateCompanyStmt() throws SQLException {
        if (createCompany == null) {
            createCompany = getConnection().prepareStatement(INSERT_COMPANY_QUERY);
        }
        return createCompany;
    }

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

    public ResultSet getAllAgentsCommand(){
        try(PreparedStatement statement = getConnection().prepareStatement(SELECT_AGENT_QUERY)){
            return statement.executeQuery();
        }
          catch(Exception e){
            e.printStackTrace();
            return null;
          }
    }

    public ResultSet getAgentByIdCommand(int id){
        try(PreparedStatement statement = getConnection().prepareStatement(SELECT_AGENT_BY_ID_QUERY);){

            statement.setInt(1, id);

            return statement.executeQuery();
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //BETTER PRACTICE CODE -> ALWAYS CLOSE CONNECTION
    public int updateAgentSalaryCommand(int id, double salary) {

        try (PreparedStatement stmt =
                     getConnection().prepareStatement(UPDATE_AGENT_SALARY_QUERY)) {

            stmt.setDouble(1, salary);
            stmt.setInt(2, id);

            return stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int deleteAgent(int id){
        try(PreparedStatement statement = getConnection().prepareStatement(DELETE_AGENT_QUERY)) {
            statement.setInt(1, id);

            return statement.executeUpdate();
        }
        catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public ResultSet getAllApartmentsCommand() {
        try (PreparedStatement statement = getConnection().prepareStatement(SELECT_ALL_APARTMENTS_QUERY)) {
            return statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}