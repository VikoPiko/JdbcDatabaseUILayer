package com.ru.mag.db.jdbc.controllers;

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

public class ClientController {

    @FXML public TextField personId;
    @FXML public TextField firstName;
    @FXML public TextField lastName;
    @FXML public TextField email;
    @FXML public TextField phoneNumber;
    @FXML public TextField budget;
    @FXML public TextField areaInterestedIn;

    public void showAllClients(ActionEvent event) throws IOException {
        try{
            //create a resultset getting the db instance command -> goto db util
            ResultSet rs = DBUtil.getInstance().getAllClientsCommand();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TableDialog.fxml"));
            Parent tableParent = fxmlLoader.load();

            TableController tableController = fxmlLoader.getController();
            tableController.setTableResultset(rs, "All Clients");

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Clients");
            stage.setScene(new Scene(tableParent));
            stage.show();

        } catch (Exception e){
            showError(e.getMessage());
        }
    }

    public void findUserById(ActionEvent event) throws IOException {
        try{
            if (personId.getText().isEmpty()) {
                showError("Enter Person ID first");
                return;
            }
            int id = Integer.parseInt(personId.getText());

            ResultSet rs = DBUtil.getInstance().getPersonById(id);
            ResultSet rsAgent = DBUtil.getInstance().getClientByIdCommand(id);
            if(rsAgent != null && rsAgent.next()){
                budget.setText(rsAgent.getString("budget"));
                areaInterestedIn.setText(rsAgent.getString("area_interested_in"));
            }
            if (rs != null && rs.next()) {
                firstName.setText(rs.getString("first_name"));
                lastName.setText(rs.getString("last_name"));
                email.setText(rs.getString("email"));
                phoneNumber.setText(rs.getString("phone_number"));
            } else {
                showError("Person not found");
            }
        } catch(Exception e){
            showError(e.getMessage());
        }
    }

    public void handleCreate() {
        try {
            if(personId.getText().isEmpty()){
                showError("Enter Person ID first!\nPerson must exist first.");
            }
            if(areaInterestedIn == null || budget == null) {
                showError("Please fill all the fields");
            }
            double budgetVal = Double.parseDouble(budget.getText());
            int id = Integer.parseInt(personId.getText());
            DBUtil.getInstance().insertClient(
                    id,budgetVal, areaInterestedIn.getText()
            );
            showInfo("Client created");
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    public void handleUpdate() {
        try {
            if(personId.getText().isEmpty()){
                showError("Enter Person ID first!\nPerson must be selected.");
            }
            if(areaInterestedIn == null || budget == null) {
                showError("Please fill all the fields");
            }
            double budgetVal = Double.parseDouble(budget.getText());
            int id = Integer.parseInt(personId.getText());
            DBUtil.getInstance().updateClient(
                    id, budgetVal, areaInterestedIn.getText()
            );
            showInfo("Client with ID: " + personId.getText() + " updated");
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    public void handleDelete(){
        try {
            if(personId.getText().isEmpty()){
                showError("Enter Person ID first!\nPerson must be selected.");
            }
            int id = Integer.parseInt(personId.getText());
            DBUtil.getInstance().deleteClient(id);
            showInfo("Client with ID: " + personId.getText() + " DELETED!");
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private void showInfo(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg).show();
    }

    private void showError(String msg) {
        new Alert(Alert.AlertType.ERROR, msg).show();
    }
}
