package com.ru.mag.db.jdbc.controllers;

import com.ru.mag.db.jdbc.queries.PropertyImageQueries;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.ResultSet;

public class PropertyImageController {
    @FXML private TextField listingId;
    @FXML
    private TextField imageUrl;

    @FXML private ImageView imagePreview;
    private byte[] selectedImageBytes;

    private File selectedFile;
    PropertyImageQueries repo = new PropertyImageQueries();

//    public void chooseImage(){
//        FileChooser fc = new FileChooser();
//        fc.getExtensionFilters().add(
//                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg")
//        );
//        selectedFile = fc.showOpenDialog(null);
//    }

    @FXML
    private void chooseImage() throws IOException {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg")
        );

        File file = fc.showOpenDialog(null);
        if (file != null) {
            Image img = new Image(file.toURI().toString());
            imagePreview.setImage(img);
            selectedImageBytes = Files.readAllBytes(file.toPath());
        }
    }

    public void uploadImage(){
        try{
            if(selectedImageBytes == null){
                showError("Select image first");
                return;
            }

            if(selectedImageBytes.length > 4_000_000){
                showError("Image too large (max 4MB)");
                return;
            }

            repo.insertImage(
                    Integer.parseInt(listingId.getText()),
                    selectedImageBytes,
                    imageUrl.getText()
            );

            showInfo("Image uploaded");

            // optional reset
            selectedImageBytes = null;
            imagePreview.setImage(null);

        } catch(Exception e){
            showError(e.getMessage());
        }
    }

    public void getAllImages(){
        try{
            openTable(repo.getAllImages(), "All Images");
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

    private void showInfo(String m){ new Alert(Alert.AlertType.INFORMATION,m).show(); }
    private void showError(String m){ new Alert(Alert.AlertType.ERROR,m).show(); }
}
