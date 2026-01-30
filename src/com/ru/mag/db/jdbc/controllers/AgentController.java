package com.ru.mag.db.jdbc.controllers;

import com.ru.mag.db.jdbc.models.Agent;
import com.ru.mag.db.jdbc.queries.AgentQueries;
import com.ru.mag.db.jdbc.util.DBUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
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

    AgentQueries agentRepo = new AgentQueries();

    @FXML
    public void findUserById(ActionEvent event) throws IOException {
        try{
            if (personId.getText().isEmpty()) {
                showError("Enter Person ID first");
                return;
            }
            int id = Integer.parseInt(personId.getText());

            ResultSet rs = DBUtil.getInstance().getPersonById(id);
            ResultSet rsAgent = agentRepo.getAgentByIdCommand(id);
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

    @FXML
    public void getAgents(ActionEvent event) throws IOException {
        try {
            ResultSet rs = agentRepo.getAllAgentsCommand();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/TableDialog.fxml"));
            Parent tableParent = loader.load();

            TableController tableController = loader.getController();
            tableController.setTableResultset(rs, "All Agents");

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Agents");
            stage.setScene(new Scene(tableParent));
            stage.show();

        } catch (Exception e) {
            showError(e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void createAgent(ActionEvent event) {
        try {
            Agent agent = new Agent(
                    Integer.parseInt(personId.getText()),
                    Double.parseDouble(salary.getText()),
                    Date.valueOf(hireDate.getValue())
            );
            agentRepo.createAgentCommand(agent);
            showInfo("Agent created successfully!");
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void updateAgent(ActionEvent event) {
        try {
            Agent agent = new Agent(
                    Integer.parseInt(personId.getText()),
                    Double.parseDouble(salary.getText()),
                    Date.valueOf(hireDate.getValue())
            );
            agentRepo.updateAgentCommand(agent);
            showInfo("Agent with ID: " + agent.getId() + " updated successfully!");
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void deleteAgent(ActionEvent event) {
        try {
            Agent agent = new Agent(
                    Integer.parseInt(personId.getText()),
                    Double.parseDouble(salary.getText()),
                    Date.valueOf(hireDate.getValue())
            );
            agentRepo.deleteAgentCommand(agent);
            showInfo("Agent with ID: "+ agent.getId() +" deleted successfully!");
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
