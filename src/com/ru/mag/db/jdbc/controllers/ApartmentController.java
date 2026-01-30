package com.ru.mag.db.jdbc.controllers;

import com.ru.mag.db.jdbc.queries.ApartmentQueries;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.sql.ResultSet;

public class ApartmentController {
    @FXML
    private TextField propertyId;
    @FXML
    private TextField rooms;
    @FXML
    private TextField floor;
    @FXML
    private TextField bathrooms;

    ApartmentQueries apartmentRepo = new ApartmentQueries();

    @FXML
    void createApartment(ActionEvent e){
        try{
            apartmentRepo.createApartment(
                    Integer.parseInt(propertyId.getText()),
                    Integer.parseInt(floor.getText()),
                    Integer.parseInt(bathrooms.getText()),
                    Integer.parseInt(rooms.getText())
            );
            showInfo("Apartment created");
        } catch(Exception ex){
            showError(ex.getMessage());
        }
    }

    @FXML
    public void getApartments(ActionEvent event){
        try{
            ResultSet rs = apartmentRepo.getApartments();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/TableDialog.fxml"));
            Parent tableParent = loader.load();

            TableController tableController = loader.getController();
            tableController.setTableResultset(rs, "All Apartments");

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Apartments");
            stage.setScene(new Scene(tableParent));
            stage.show();

        } catch(Exception e){
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
