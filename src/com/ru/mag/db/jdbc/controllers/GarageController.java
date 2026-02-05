package com.ru.mag.db.jdbc.controllers;

import com.ru.mag.db.jdbc.queries.GarageQueries;
import com.ru.mag.db.jdbc.util.DBUtil;
import com.ru.mag.db.jdbc.util.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.sql.ResultSet;

public class GarageController {

    @FXML
    TextField property_id;

    GarageQueries repo = new GarageQueries();

    public void handleCreate() {
        try {
            if(property_id.getText().isEmpty()){
                showError("Enter a Property ID first!\nProperty must exist first.");
            }
            int id = Integer.parseInt(property_id.getText());
            repo.createGarage(id);
            showInfo("Garage created");
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    public void handleDelete() {
        try{
            if(property_id.getText().isEmpty()){
                showError("Enter a Property ID first!");
            }
            int id = Integer.parseInt(property_id.getText());
            repo.deleteGarage(id);
            showInfo("Garage deleted");
        } catch(Exception e){
            showError(e.getMessage());
        }
    }

    public void showAllGarages(ActionEvent event) throws IOException {
        try{
            ResultSet rs = repo.getAllGarages();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/TableDialog.fxml"));
            Parent tableParent = fxmlLoader.load();

            TableController tableController = fxmlLoader.getController();
            tableController.setTableResultset(rs, "All Garages");

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Garages");
            stage.setScene(new Scene(tableParent));
            stage.show();

        } catch (Exception e){
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
