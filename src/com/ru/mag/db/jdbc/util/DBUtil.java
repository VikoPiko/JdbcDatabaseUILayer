package com.ru.mag.db.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtil {

    // -----------------------------
    // Cached PreparedStatements
    // -----------------------------
    private PreparedStatement createCompany = null;
    private PreparedStatement getCompanyById = null;
    private PreparedStatement updateCompanyPhone = null;
    private PreparedStatement deleteCompany = null;

    private PreparedStatement createProduct = null;
    private PreparedStatement getProductById = null;
    private PreparedStatement updateProduct = null;
    private PreparedStatement deleteProduct = null;
    private PreparedStatement listProductsByManufacturer = null;
    private PreparedStatement getAllProducts = null;

    private PreparedStatement createProductPrice = null;
    private PreparedStatement getCurrentPriceForProduct = null;

    private PreparedStatement getLowStockBatches = null;

    private PreparedStatement createDelivery = null;
    private PreparedStatement addDeliveryProduct = null;
    private PreparedStatement getDeliveryHeader = null;
    private PreparedStatement getDeliveryLines = null;

    // -----------------------------
    // SQL Queries
    // -----------------------------
    // COMPANY CRUD
    private static final String INSERT_COMPANY_QUERY =
            "INSERT INTO Company(company_id, name, eik, address, phone) " +
                    "VALUES(?, ?, ?, address_t(?, ?, ?, ?), ?)";

    private static final String SELECT_COMPANY_BY_ID_QUERY =
            "SELECT c.company_id, c.name, c.eik, " +
                    "       c.address.country AS country, c.address.city AS city, " +
                    "       c.address.street_address AS street_address, c.address.postal_code AS postal_code, " +
                    "       c.phone " +
                    "FROM Company c WHERE c.company_id = ?";

    private static final String UPDATE_COMPANY_PHONE_QUERY =
            "UPDATE Company SET phone = ? WHERE company_id = ?";

    private static final String DELETE_COMPANY_QUERY =
            "DELETE FROM Company WHERE company_id = ?";

    // PRODUCT CRUD
    private static final String INSERT_PRODUCT_QUERY =
            "INSERT INTO Product(product_id, manufacturer_id, name, form, gtin) VALUES(?, ?, ?, ?, ?)";

    private static final String SELECT_PRODUCT_BY_ID_QUERY =
            "SELECT p.product_id, p.manufacturer_id, p.name, p.form, p.gtin " +
                    "FROM Product p WHERE p.product_id = ?";

    private static final String UPDATE_PRODUCT_QUERY =
            "UPDATE Product SET manufacturer_id = ?, name = ?, form = ?, gtin = ? WHERE product_id = ?";

    private static final String DELETE_PRODUCT_QUERY =
            "DELETE FROM Product WHERE product_id = ?";

    private static final String LIST_PRODUCTS_BY_MANUFACTURER_QUERY =
            "SELECT p.product_id, p.name, p.form, p.gtin " +
                    "FROM Product p WHERE p.manufacturer_id = ? ORDER BY p.name";

    private static final String SELECT_ALL_PRODUCTS_QUERY =
            "SELECT p.product_id, p.manufacturer_id, p.name, p.form, p.gtin " +
                    "FROM Product p ORDER BY p.product_id";

    // PRODUCT PRICE
    private static final String INSERT_PRODUCT_PRICE_QUERY =
            "INSERT INTO Product_Price(price_id, product_id, valid_from, valid_to, price) " +
                    "VALUES(?, ?, ?, ?, ?)";

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
//                DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
                cachedConnection = DriverManager.getConnection(
                        "jdbc:oracle:thin:@localhost:1521:xe",
                        "AptekaKursova",
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
    // -----------------------------
    private PreparedStatement getCreateCompanyStmt() throws SQLException {
        if (createCompany == null) {
            createCompany = getConnection().prepareStatement(INSERT_COMPANY_QUERY);
        }
        return createCompany;
    }

    private PreparedStatement getCompanyByIdStmt() throws SQLException {
        if (getCompanyById == null) {
            getCompanyById = getConnection().prepareStatement(SELECT_COMPANY_BY_ID_QUERY);
        }
        return getCompanyById;
    }

    private PreparedStatement getUpdateCompanyPhoneStmt() throws SQLException {
        if (updateCompanyPhone == null) {
            updateCompanyPhone = getConnection().prepareStatement(UPDATE_COMPANY_PHONE_QUERY);
        }
        return updateCompanyPhone;
    }

    private PreparedStatement getDeleteCompanyStmt() throws SQLException {
        if (deleteCompany == null) {
            deleteCompany = getConnection().prepareStatement(DELETE_COMPANY_QUERY);
        }
        return deleteCompany;
    }

    private PreparedStatement getCreateProductStmt() throws SQLException {
        if (createProduct == null) {
            createProduct = getConnection().prepareStatement(INSERT_PRODUCT_QUERY);
        }
        return createProduct;
    }

    private PreparedStatement getProductByIdStmt() throws SQLException {
        if (getProductById == null) {
            getProductById = getConnection().prepareStatement(SELECT_PRODUCT_BY_ID_QUERY);
        }
        return getProductById;
    }

    private PreparedStatement getAllProductsStmt() throws SQLException {
        if (getAllProducts == null) {
            getAllProducts = getConnection().prepareStatement(SELECT_ALL_PRODUCTS_QUERY);
        }
        return getAllProducts;
    }


    private PreparedStatement getUpdateProductStmt() throws SQLException {
        if (updateProduct == null) {
            updateProduct = getConnection().prepareStatement(UPDATE_PRODUCT_QUERY);
        }
        return updateProduct;
    }

    private PreparedStatement getDeleteProductStmt() throws SQLException {
        if (deleteProduct == null) {
            deleteProduct = getConnection().prepareStatement(DELETE_PRODUCT_QUERY);
        }
        return deleteProduct;
    }

    private PreparedStatement getListProductsByManufacturerStmt() throws SQLException {
        if (listProductsByManufacturer == null) {
            listProductsByManufacturer = getConnection().prepareStatement(LIST_PRODUCTS_BY_MANUFACTURER_QUERY);
        }
        return listProductsByManufacturer;
    }

    private PreparedStatement getCreateProductPriceStmt() throws SQLException {
        if (createProductPrice == null) {
            createProductPrice = getConnection().prepareStatement(INSERT_PRODUCT_PRICE_QUERY);
        }
        return createProductPrice;
    }

    private PreparedStatement getCurrentPriceStmt() throws SQLException {
        if (getCurrentPriceForProduct == null) {
            getCurrentPriceForProduct = getConnection().prepareStatement(SELECT_CURRENT_PRICE_QUERY);
        }
        return getCurrentPriceForProduct;
    }

    private PreparedStatement getLowStockStmt() throws SQLException {
        if (getLowStockBatches == null) {
            getLowStockBatches = getConnection().prepareStatement(SELECT_LOW_STOCK_BATCHES_QUERY);
        }
        return getLowStockBatches;
    }

    private PreparedStatement getCreateDeliveryStmt() throws SQLException {
        if (createDelivery == null) {
            createDelivery = getConnection().prepareStatement(INSERT_DELIVERY_QUERY);
        }
        return createDelivery;
    }

    private PreparedStatement getAddDeliveryProductStmt() throws SQLException {
        if (addDeliveryProduct == null) {
            addDeliveryProduct = getConnection().prepareStatement(INSERT_DELIVERY_PRODUCT_QUERY);
        }
        return addDeliveryProduct;
    }

    private PreparedStatement getDeliveryHeaderStmt() throws SQLException {
        if (getDeliveryHeader == null) {
            getDeliveryHeader = getConnection().prepareStatement(SELECT_DELIVERY_HEADER_QUERY);
        }
        return getDeliveryHeader;
    }

    private PreparedStatement getDeliveryLinesStmt() throws SQLException {
        if (getDeliveryLines == null) {
            getDeliveryLines = getConnection().prepareStatement(SELECT_DELIVERY_LINES_QUERY);
        }
        return getDeliveryLines;
    }

    // -----------------------------
    // CRUD METHODS: COMPANY
    // -----------------------------
    public int createCompany(
            int companyId, String name, String eik,
            String country, String city, String streetAddress, String postalCode,
            String phone
    ) {
        try {
            PreparedStatement stmt = getCreateCompanyStmt();
            stmt.setInt(1, companyId);
            stmt.setString(2, name);
            stmt.setString(3, eik);
            stmt.setString(4, country);
            stmt.setString(5, city);
            stmt.setString(6, streetAddress);
            stmt.setString(7, postalCode);
            stmt.setString(8, phone);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public ResultSet getCompanyById(int companyId) {
        try {
            PreparedStatement stmt = getCompanyByIdStmt();
            stmt.setInt(1, companyId);
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int updateCompanyPhone(int companyId, String newPhone) {
        try {
            PreparedStatement stmt = getUpdateCompanyPhoneStmt();
            stmt.setString(1, newPhone);
            stmt.setInt(2, companyId);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int deleteCompany(int companyId) {
        try {
            PreparedStatement stmt = getDeleteCompanyStmt();
            stmt.setInt(1, companyId);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    // -----------------------------
    // CRUD METHODS: PRODUCT
    // -----------------------------
    public int createProduct(int productId, int manufacturerCompanyId, String name, String form, String gtin) {
        try {
            PreparedStatement stmt = getCreateProductStmt();
            stmt.setInt(1, productId);
            stmt.setInt(2, manufacturerCompanyId);
            stmt.setString(3, name);
            stmt.setString(4, form);
            stmt.setString(5, gtin);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public ResultSet getProductById(int productId) {
        try {
            PreparedStatement stmt = getProductByIdStmt();
            stmt.setInt(1, productId);
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet getAllProducts() {
        try {
            PreparedStatement stmt = getAllProductsStmt();
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public int updateProduct(int productId, int manufacturerCompanyId, String name, String form, String gtin) {
        try {
            PreparedStatement stmt = getUpdateProductStmt();
            stmt.setInt(1, manufacturerCompanyId);
            stmt.setString(2, name);
            stmt.setString(3, form);
            stmt.setString(4, gtin);
            stmt.setInt(5, productId);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int deleteProduct(int productId) {
        try {
            PreparedStatement stmt = getDeleteProductStmt();
            stmt.setInt(1, productId);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public ResultSet listProductsByManufacturer(int manufacturerCompanyId) {
        try {
            PreparedStatement stmt = getListProductsByManufacturerStmt();
            stmt.setInt(1, manufacturerCompanyId);
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // -----------------------------
    // EXTRA QUERIES (useful)
    // -----------------------------
    public int createProductPrice(int priceId, int productId, java.sql.Date validFrom, java.sql.Date validTo, double price) {
        try {
            PreparedStatement stmt = getCreateProductPriceStmt();
            stmt.setInt(1, priceId);
            stmt.setInt(2, productId);
            stmt.setDate(3, validFrom);
            stmt.setDate(4, validTo);   // can be null
            stmt.setDouble(5, price);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public ResultSet getCurrentPriceForProduct(int productId) {
        try {
            PreparedStatement stmt = getCurrentPriceStmt();
            stmt.setInt(1, productId);
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet getLowStockBatches() {
        try {
            PreparedStatement stmt = getLowStockStmt();
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // -----------------------------
    // DELIVERY: create + details
    // -----------------------------
    public int createDelivery(int deliveryId, int customerCompanyId, String docNo,
                              java.sql.Date deliveredAt, String vehiclePlate, String carrier, String note) {
        try {
            PreparedStatement stmt = getCreateDeliveryStmt();
            stmt.setInt(1, deliveryId);
            stmt.setInt(2, customerCompanyId);
            stmt.setString(3, docNo);
            stmt.setDate(4, deliveredAt);
            stmt.setString(5, vehiclePlate);
            stmt.setString(6, carrier);
            stmt.setString(7, note);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int addDeliveryProduct(int deliveryProductId, int deliveryId, int productId, int batchId,
                                  double qty, double unitPrice) {
        try {
            PreparedStatement stmt = getAddDeliveryProductStmt();
            stmt.setInt(1, deliveryProductId);
            stmt.setInt(2, deliveryId);
            stmt.setInt(3, productId);
            stmt.setInt(4, batchId);
            stmt.setDouble(5, qty);
            stmt.setDouble(6, unitPrice);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public ResultSet getDeliveryHeader(int deliveryId) {
        try {
            PreparedStatement stmt = getDeliveryHeaderStmt();
            stmt.setInt(1, deliveryId);
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet getDeliveryLines(int deliveryId) {
        try {
            PreparedStatement stmt = getDeliveryLinesStmt();
            stmt.setInt(1, deliveryId);
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
