package com.ru.mag.db.jdbc.controllers;

import com.ru.mag.db.jdbc.queries.PersonQueries;
import com.ru.mag.db.jdbc.util.DBUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonController {
    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private TextField email;
    @FXML private TextField phoneNumber;

    @FXML private TextField personId;

    private Integer currentPersonId;

    PersonQueries repo = new PersonQueries();

    public void handleLoadPerson() {
        try {
            if (personId.getText().isEmpty()) {
                showError("Enter Person ID first");
                return;
            }
            int id = Integer.parseInt(personId.getText());

            ResultSet rs = DBUtil.getInstance().getPersonById(id);
            if (rs != null && rs.next()) {
                currentPersonId = id;
                firstName.setText(rs.getString("first_name"));
                lastName.setText(rs.getString("last_name"));
                email.setText(rs.getString("email"));
                phoneNumber.setText(rs.getString("phone_number"));
            } else {
                showError("Person not found");
            }
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    public void getAll(){
        try{
            ResultSet rs = repo.getALlPeople();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/TableDialog.fxml"));
            Parent tableParent = loader.load();

            TableController tableController = loader.getController();
            tableController.setTableResultset(rs, "All People");

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("People");
            stage.setScene(new Scene(tableParent));
            stage.show();


        } catch(Exception e){
            showError(e.getMessage());
        }
    }



    public void handleCreate() {
        try {
            if(firstName == null || lastName == null || email == null || phoneNumber == null) {
                showError("Please fill all the fields");
            }
            DBUtil.getInstance().insertPerson(
                    firstName.getText(),
                    lastName.getText(),
                    email.getText(),
                    phoneNumber.getText()
            );
            showInfo("Person created");
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    public void handleUpdate() {
        try {
            DBUtil.getInstance().updatePerson(
                    currentPersonId,
                    firstName.getText(),
                    lastName.getText(),
                    email.getText(),
                    phoneNumber.getText()
            );
            showInfo("Person updated");
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

//    public void handleDelete() {
//        try {
//            DBUtil.getInstance().deletePerson(currentPersonId);
//            showInfo("Person deleted");
//        } catch (Exception e) {
//            showError(e.getMessage());
//        }
//    }

    public void setPersonData(ResultSet rs) throws SQLException {
        if (rs.next()) {
            currentPersonId = rs.getInt("person_id");
            firstName.setText(rs.getString("first_name"));
            lastName.setText(rs.getString("last_name"));
            email.setText(rs.getString("email"));
            phoneNumber.setText(rs.getString("phone_number"));
        }
    }

    private void showInfo(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg).show();
    }

    private void showError(String msg) {
        new Alert(Alert.AlertType.ERROR, msg).show();
    }

}
