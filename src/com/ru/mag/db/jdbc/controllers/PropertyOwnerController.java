package com.ru.mag.db.jdbc.controllers;

import com.ru.mag.db.jdbc.models.Person;
import com.ru.mag.db.jdbc.util.DBUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class PropertyOwnerController implements Initializable {

    @FXML
    ComboBox<Person> personSelector;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            List<Person> people = DBUtil.getInstance().getShortenedPeopleCommand();
            personSelector.getItems().addAll(people);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createOwner(){
        try{

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
