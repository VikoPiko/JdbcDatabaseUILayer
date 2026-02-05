package com.ru.mag.db.jdbc.controllers;

import com.ru.mag.db.jdbc.queries.RolesQueries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import oracle.sql.STRUCT;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class RolesController {

    @FXML private TextField roleId;
    @FXML private TextField roleType;
    @FXML private TextField hasFullAccess;
    @FXML private TextField canPost;
    @FXML private TextField canAuthorizeSale;

    RolesQueries repo = new RolesQueries();

    public void createRole() {
        try {
            repo.createRole(
                    roleType.getText(),
                    Integer.parseInt(hasFullAccess.getText()),
                    Integer.parseInt(canPost.getText()),
                    Integer.parseInt(canAuthorizeSale.getText())
            );
            showInfo("Role created");
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    public void getAllRoles() {
        try {
            ResultSet rs = repo.getAllRoles();
            ObservableList<ObservableList<String>> tableData = FXCollections.observableArrayList();
            ObservableList<String> columnNames = FXCollections.observableArrayList();

            int columnCount = rs.getMetaData().getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                if ("ROLE_PRIVILAGES".equalsIgnoreCase(rs.getMetaData().getColumnName(i))) {
                    columnNames.add("HAS_FULL_ACCESS");
                    columnNames.add("CAN_POST");
                    columnNames.add("CAN_AUTHORIZE_SALE");
                } else {
                    columnNames.add(rs.getMetaData().getColumnName(i));
                }
            }

            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();

                for (int i = 1; i <= columnCount; i++) {
                    if ("ROLE_PRIVILAGES".equalsIgnoreCase(rs.getMetaData().getColumnName(i))) {
                        STRUCT struct = (STRUCT) rs.getObject(i);
                        int[] privs = RolesQueries.extractPrivileges(struct);
                        row.add(String.valueOf(privs[0]));
                        row.add(String.valueOf(privs[1]));
                        row.add(String.valueOf(privs[2]));
                    } else {
                        row.add(rs.getString(i));
                    }
                }

                tableData.add(row);
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/TableDialog.fxml"));
            Parent parent = loader.load();
            TableController tableController = loader.getController();

            tableController.setTableResultsetFromListWithHeaders(tableData, columnNames, "All Roles");

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("All Roles");
            stage.setScene(new Scene(parent));
            stage.show();

        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    public void deleteRole() {
        try {
            int id = Integer.parseInt(roleId.getText());
            repo.deleteRole(id);
            showInfo("Role deleted");
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
