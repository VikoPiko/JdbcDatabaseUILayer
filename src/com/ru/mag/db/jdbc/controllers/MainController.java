package com.ru.mag.db.jdbc.controllers;

import com.ru.mag.db.jdbc.queries.AgentQueries;
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

public class MainController {

    @FXML
    private TextField agentIdField;

    public void showAllPeople(ActionEvent event) throws IOException{
        try{
            ResultSet rs = DBUtil.getInstance().getAllPeopleCommand();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/TableDialog.fxml"));
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
            AgentQueries agentRepo = new AgentQueries();
            //create a resultset getting the db instance command -> goto db util
            ResultSet rs = agentRepo.getAllAgentsCommand();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/TableDialog.fxml"));
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


    public void openProperties(ActionEvent event) throws IOException {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/PropertyDialog.fxml"));
            Parent tableParent = fxmlLoader.load();

            PropertyController propertyController = fxmlLoader.getController();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Property");
            stage.setScene(new Scene(tableParent));
            stage.show();
        }catch(Exception e){
            showError(e.getMessage());
        }
    }

    public void openPropOwnerForm(ActionEvent event) throws IOException {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/PropertyOwnerDialog.fxml"));
            Parent tableParent = fxmlLoader.load();

            PropertyOwnerController propertyOwnerController = fxmlLoader.getController();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Property");
            stage.setScene(new Scene(tableParent));
            stage.show();
        }catch(Exception e){
            showError(e.getMessage());
        }
    }

    public void openClientsForm(ActionEvent event) throws IOException {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/ClientDialog.fxml"));
            Parent tableParent = fxmlLoader.load();

            ClientController clientController = fxmlLoader.getController();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Clients");
            stage.setScene(new Scene(tableParent));
            stage.show();
        }catch(Exception e){
            showError(e.getMessage());
        }
    }

    public void showAgentById(ActionEvent event) throws IOException {
        try{
            AgentQueries agentRepo = new AgentQueries();
            int agentId = Integer.parseInt(agentIdField.getText());
            //create a resultset getting the dbinstance command -> goto dbutil
            ResultSet rs = agentRepo.getAgentByIdCommand(agentId);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/TableDialog.fxml"));
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/PersonDialog.fxml"));
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

    public void openAgentForm(ActionEvent event) throws IOException {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/AgentDialog.fxml"));
            Parent tableParent = fxmlLoader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Agent Editor");
            stage.setScene(new Scene(tableParent));
            stage.show();
        }catch(Exception e){
            showError(e.getMessage());
        }
    }

    public void searchProduct(ActionEvent event) throws IOException {
        try {
            // Example: hardcoded ID for exercise simplicity
            int productId = 10;

//            ResultSet rs = DBUtil.getInstance().getProductById(productId);

            FXMLLoader fxmlLoader =
                    new FXMLLoader(getClass().getResource("../gui/TableDialog.fxml"));
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

    public void openApartmentForm(ActionEvent event) throws IOException {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/ApartmentDialog.fxml"));
            Parent tableParent = fxmlLoader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Apartment Management");
            stage.setScene(new Scene(tableParent));
            stage.show();
        }catch(Exception e){
            showError(e.getMessage());
        }
    }
    public void openListingForm(ActionEvent event) throws IOException {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/ListingDialog.fxml"));
            Parent tableParent = fxmlLoader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Apartment Management");
            stage.setScene(new Scene(tableParent));
            stage.show();
        }catch(Exception e){
            showError(e.getMessage());
        }
    }

    public void openHouseForm(ActionEvent event) throws IOException {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/HouseDialog.fxml"));
            Parent tableParent = fxmlLoader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Apartment Management");
            stage.setScene(new Scene(tableParent));
            stage.show();
        }catch(Exception e){
            showError(e.getMessage());
        }
    }

    public void openGarageForm(ActionEvent event) throws IOException {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/GarageDialog.fxml"));
            Parent tableParent = fxmlLoader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Apartment Management");
            stage.setScene(new Scene(tableParent));
            stage.show();
        }catch(Exception e){
            showError(e.getMessage());
        }
    }

    public void openPreferencesForm(ActionEvent event) throws IOException {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/PreferencesDialog.fxml"));
            Parent tableParent = fxmlLoader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Apartment Management");
            stage.setScene(new Scene(tableParent));
            stage.show();
        }catch(Exception e){
            showError(e.getMessage());
        }
    }

    public void openSuccessfulDeals(ActionEvent event) throws IOException {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/SuccessfulDealsDialog.fxml"));
            Parent tableParent = fxmlLoader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Apartment Management");
            stage.setScene(new Scene(tableParent));
            stage.show();
        }catch(Exception e){
            showError(e.getMessage());
        }
    }

    public void openPropImageForm(ActionEvent event) throws IOException {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/PropertyImageDialog.fxml"));
            Parent tableParent = fxmlLoader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Apartment Management");
            stage.setScene(new Scene(tableParent));
            stage.show();
        }catch(Exception e){
            showError(e.getMessage());
        }
    }

    public void openRoles(ActionEvent event) throws IOException {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/RolesDialog.fxml"));
            Parent tableParent = fxmlLoader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Roles Management");
            stage.setScene(new Scene(tableParent));
            stage.show();
        }catch(Exception e){
            showError(e.getMessage());
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
