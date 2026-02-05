package com.ru.mag.db.jdbc.controllers;

import com.ru.mag.db.jdbc.queries.PropertyImageQueries;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

import java.io.ByteArrayInputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TableController {

    @FXML
    TableView<ObservableList<String>> tableView1;

    @FXML
    Label testLabel;
    @FXML private ImageView imagePreview;

    private final PropertyImageQueries imageRepo = new PropertyImageQueries();

    public void setTableResultset(ResultSet resultSet, String labelText) throws SQLException {

        testLabel.setText(labelText);
        tableView1.getColumns().clear();

        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
        int columnCount = resultSet.getMetaData().getColumnCount();

        for (int i = 0; i < columnCount; i++) {
            final int j = i;
            TableColumn<ObservableList<String>, String> col =
                    new TableColumn<>(resultSet.getMetaData().getColumnName(i + 1));

            col.setCellValueFactory(param -> {
                String value = param.getValue().get(j);
                return new SimpleStringProperty(value == null ? "" : value);
            });

            tableView1.getColumns().add(col);
        }

//        resultSet.beforeFirst();

        while (resultSet.next()) {
            ObservableList<String> row = FXCollections.observableArrayList();
            for (int i = 1; i <= columnCount; i++) {
                row.add(resultSet.getString(i));
            }
            data.add(row);
        }

        tableView1.setItems(data);

        if (labelText.toLowerCase().contains("image")) {
            enableImagePreview();
        }
    }

    private void enableImagePreview() {
        tableView1.getSelectionModel().selectedItemProperty().addListener((obs, oldRow, newRow) -> {
            if (newRow == null) return;

            try {
                int imageId = Integer.parseInt(newRow.get(0)); // first column = image_id
                byte[] imgBytes = imageRepo.getImageDataById(imageId);

                if (imgBytes != null) {
                    Image img = new Image(new ByteArrayInputStream(imgBytes));
                    imagePreview.setImage(img);
                } else {
                    imagePreview.setImage(null);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
