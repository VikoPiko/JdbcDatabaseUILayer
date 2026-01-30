package com.ru.mag.db.jdbc.controllers;

import com.ru.mag.db.jdbc.models.Person;
import com.ru.mag.db.jdbc.queries.PropertyOwnerQueries;
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

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class PropertyOwnerController implements Initializable {

    @FXML
    ComboBox<Person> personSelector;

    @FXML private TextField personId;
    @FXML private TextField propertyId;


    PropertyOwnerQueries repo = new PropertyOwnerQueries();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            List<Person> people = DBUtil.getInstance().getShortenedPeopleCommand();
            personSelector.getItems().addAll(people);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void assignOwner(){
        try{
            repo.assignOwner(
                    Integer.parseInt(personId.getText()),
                    Integer.parseInt(propertyId.getText())
            );
            showInfo("Owner assigned");
        } catch(Exception e){ showError(e.getMessage()); }
    }

    public void getAllOwners(){
        try{
            openTable(repo.getAllPropertyOwners(),"Property Owners");
        } catch(Exception e){ showError(e.getMessage()); }
    }

    private void openTable(ResultSet rs, String title) throws IOException {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/TableDialog.fxml"));
            Parent p = loader.load();
            loader.<TableController>getController().setTableResultset(rs, title);

            Stage s = new Stage();
            s.initModality(Modality.APPLICATION_MODAL);
            s.setTitle(title);
            s.setScene(new Scene(p));
            s.show();
        } catch(Exception e){ showError(e.getMessage()); }
    }


    private void showInfo(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg).show();
    }

    private void showError(String msg) {
        new Alert(Alert.AlertType.ERROR, msg).show();
    }
}
