package com.ru.mag.db.jdbc.gui;

import com.ru.mag.db.jdbc.util.DBUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;

public class AgentController {
    @FXML private TextField salary;
    @FXML private DatePicker hireDate;
    @FXML private TextField personId;
    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private TextField email;
    @FXML private TextField phoneNumber;


    public void findUserById(ActionEvent event) throws IOException {
        try{
            if (personId.getText().isEmpty()) {
                showError("Enter Person ID first");
                return;
            }
            int id = Integer.parseInt(personId.getText());

            ResultSet rs = DBUtil.getInstance().getPersonById(id);
            ResultSet rsAgent = DBUtil.getInstance().getAgentByIdCommand(id);
            if(rsAgent != null && rsAgent.next()){
                salary.setText(rsAgent.getString("salary"));
                Date date = rsAgent.getDate("hire_date");
                hireDate.setValue(date.toLocalDate());
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

    private void showInfo(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg).show();
    }

    private void showError(String msg) {
        new Alert(Alert.AlertType.ERROR, msg).show();
    }

}
