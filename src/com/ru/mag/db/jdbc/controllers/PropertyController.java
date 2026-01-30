package com.ru.mag.db.jdbc.controllers;

import com.ru.mag.db.jdbc.util.DBUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class PropertyController implements Initializable {

    @FXML private TextField price;
    @FXML private TextField location;
    @FXML private TextField squareMeters;
    @FXML private TextField propertyId;
    @FXML private TextField ownerId;

    @FXML private ComboBox<String> propertyTypeSelect;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        propertyTypeSelect.getItems().addAll(
                "garage", "house", "apartment"
        );
        propertyTypeSelect.setOnAction(e -> {
            String selected = propertyTypeSelect.getValue();
            System.out.println("Selected: " + selected);
        });
    }

    public void findByOwnerId(){
        try{
            if (ownerId.getText().isEmpty()) {
                showError("Enter Owner ID first");
                return;
            }
            int id = Integer.parseInt(ownerId.getText());

            ResultSet rs = DBUtil.getInstance().getPersonById(id);
            ResultSet rsOwnerProperties = DBUtil.getInstance().getPropertyByOwnerId(id);
            if(rsOwnerProperties != null && rsOwnerProperties.next()){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/TableDialog.fxml"));
                Parent tableParent = loader.load();

                TableController tableController = loader.getController();
                tableController.setTableResultset(rsOwnerProperties, "All properties owned by Owner: " + id);

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Properties owned by Owner");

                stage.setScene(new Scene(tableParent));
                stage.show();


                price.setText(rsOwnerProperties.getString("price"));
                location.setText(rsOwnerProperties.getString("location"));
                String property_type = rsOwnerProperties.getString("property_type");
                propertyTypeSelect.setValue(property_type);
                squareMeters.setText(rsOwnerProperties.getString("location"));

            } else {
                showError("Owner not found");
            }
        }catch(Exception e){
            showError(e.getMessage());
        }
    }

    public void createProperty() {
        try {
            String type = propertyTypeSelect.getValue();

            if (type == null) {
                showError("Select property type");
                return;
            }

            double priceVal = Double.parseDouble(price.getText());
            int sqm = Integer.parseInt(squareMeters.getText());
            int owner = Integer.parseInt(ownerId.getText());
            String loc = location.getText();

            DBUtil.getInstance().insertProperty(
                    priceVal,
                    sqm,
                    loc,
                    type,
                    owner
            );

            showInfo("Property created");

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
