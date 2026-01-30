package com.ru.mag.db.jdbc.controllers;

import com.ru.mag.db.jdbc.models.House;
import com.ru.mag.db.jdbc.queries.HouseQueries;
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

public class HouseController {
    @FXML private TextField propertyId;
    @FXML private TextField floors;
    @FXML private TextField garden;
    @FXML private TextField baths;
    @FXML private TextField rooms;

    HouseQueries repo = new HouseQueries();

    @FXML
    void createHouse(ActionEvent e) {
        try {
            House h = new House();
            h.setPropertyId(Integer.parseInt(propertyId.getText()));
            h.setNumberOfFloors(Integer.parseInt(floors.getText()));
            h.setGardenSize(Integer.parseInt(garden.getText()));
            h.setBathrooms(Integer.parseInt(baths.getText()));
            h.setRooms(Integer.parseInt(rooms.getText()));

            repo.createHouse(h);
            showInfo("House created!");
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    @FXML
    void getHouses(ActionEvent e) {
        try {
            ResultSet rs = repo.getAllHouses();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/TableDialog.fxml"));
            Parent tableParent = loader.load();

            TableController tableController = loader.getController();
            tableController.setTableResultset(rs, "All Houses");

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Houses");
            stage.setScene(new Scene(tableParent));
            stage.show();
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    @FXML
    void deleteHouse(ActionEvent e) {
        try {
            repo.deleteHouse(Integer.parseInt(propertyId.getText()));
            showInfo("House deleted");
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    private void showInfo(String m){ new Alert(Alert.AlertType.INFORMATION,m).show(); }
    private void showError(String m){ new Alert(Alert.AlertType.ERROR,m).show(); }
}
