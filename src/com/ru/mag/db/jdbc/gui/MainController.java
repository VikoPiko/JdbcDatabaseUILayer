package com.ru.mag.db.jdbc.gui;

import com.ru.mag.db.jdbc.util.DBUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainController {

    @FXML
    private TextField agentIdField;

    // -----------------------------
    // SHOW ALL PRODUCTS
    // -----------------------------
    public void showAllProducts(ActionEvent event) throws IOException {
        try {
//            ResultSet rs = DBUtil.getInstance().getAllProducts();

            FXMLLoader fxmlLoader =
                    new FXMLLoader(getClass().getResource("TableDialog.fxml"));
            Parent tableParent = fxmlLoader.load();

            TableController tableController = fxmlLoader.getController();
//            tableController.setTableResultset(rs, "All products");

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Products");
            stage.setScene(new Scene(tableParent));
            stage.show();

        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    public void showAllPeople(ActionEvent event) throws IOException{
        try{
            ResultSet rs = DBUtil.getInstance().getAllPeopleCommand();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TableDialog.fxml"));
            Parent tableParent = fxmlLoader.load();

            TableController tableController = fxmlLoader.getController();
            tableController.setTableResultset(rs, "All People");

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("People");
            stage.setScene(new Scene(tableParent));
            stage.show();
        }
        catch(Exception e){
            showError(e.getMessage());
        }
    }

    public void showAllAgents(ActionEvent event) throws IOException {
        try{
            //create a resultset getting the db instance command -> goto db util
            ResultSet rs = DBUtil.getInstance().getAllAgentsCommand();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TableDialog.fxml"));
            Parent tableParent = fxmlLoader.load();

            TableController tableController = fxmlLoader.getController();
            tableController.setTableResultset(rs, "All agents");

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Agents");
            stage.setScene(new Scene(tableParent));
            stage.show();

        } catch (Exception e){
            showError(e.getMessage());
        }
    }

    public void showAgentById(ActionEvent event) throws IOException {
        try{
            int agentId = Integer.parseInt(agentIdField.getText());
            //create a resultset getting the dbinstance command -> goto dbutil
            ResultSet rs = DBUtil.getInstance().getAgentByIdCommand(agentId);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TableDialog.fxml"));
            Parent tableParent = fxmlLoader.load();

            TableController tableController = fxmlLoader.getController();
            tableController.setTableResultset(rs, "Showing agent with ID: " + agentId);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Agents");
            stage.setScene(new Scene(tableParent));
            stage.show();

        } catch (Exception e){
            showError(e.getMessage());
        }
    }

    public void openPersonForm(ActionEvent event) throws IOException {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PersonDialog.fxml"));
            Parent tableParent = fxmlLoader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Person Editor");
            stage.setScene(new Scene(tableParent));
            stage.show();
        }
        catch(Exception e){
            showError(e.getMessage());
        }
    }

    // -----------------------------
    // SEARCH PRODUCT (example by ID)
    // -----------------------------
    public void searchProduct(ActionEvent event) throws IOException {
        try {
            // Example: hardcoded ID for exercise simplicity
            int productId = 10;

//            ResultSet rs = DBUtil.getInstance().getProductById(productId);

            FXMLLoader fxmlLoader =
                    new FXMLLoader(getClass().getResource("TableDialog.fxml"));
            Parent tableParent = fxmlLoader.load();

            TableController tableController = fxmlLoader.getController();
//            tableController.setTableResultset(rs, "Product ID = " + productId);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Search product");
            stage.setScene(new Scene(tableParent));
            stage.show();

        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    // -----------------------------
    // SHOW MANUFACTURERS
    // -----------------------------
//    public void showManufacturers(ActionEvent event) throws IOException {
//        try {
//            ResultSet rs = DBUtil.getInstance().getAllManufacturers();
//
//            FXMLLoader fxmlLoader =
//                    new FXMLLoader(getClass().getResource("TableDialog.fxml"));
//            Parent tableParent = fxmlLoader.load();
//
//            TableController tableController = fxmlLoader.getController();
//            tableController.setTableResultset(rs, "Manufacturers");
//
//            Stage stage = new Stage();
//            stage.initModality(Modality.APPLICATION_MODAL);
//            stage.setTitle("Manufacturers");
//            stage.setScene(new Scene(tableParent));
//            stage.show();
//
//        } catch (SQLException e) {
//            showError(e.getMessage());
//        }
//    }

    // -----------------------------
    // EDIT PRODUCT (placeholder)
    // -----------------------------
    public void editProduct(ActionEvent event) {
        showError("Edit product not implemented yet");
    }

    // -----------------------------
    // EDIT MANUFACTURER (placeholder)
    // -----------------------------
    public void editManufacturer(ActionEvent event) {
        showError("Edit manufacturer not implemented yet");
    }

    // -----------------------------
    // COMMON ERROR DIALOG
    // -----------------------------
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
