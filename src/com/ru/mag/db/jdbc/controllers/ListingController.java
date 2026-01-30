package com.ru.mag.db.jdbc.controllers;

import com.ru.mag.db.jdbc.queries.ListingQueries;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ListingController {
    @FXML
    private TextField type;
    @FXML private TextField description;
    @FXML private TextField notes;

    ListingQueries repo = new ListingQueries();

    public void createListing(){
        try{
            repo.createListing(type.getText(), description.getText(), notes.getText());
            showInfo("Listing created");
        } catch(Exception e){ showError(e.getMessage()); }
    }

    public void getAllListings(){
        try{
            ResultSet rs = repo.getAllListings();
            openTable(rs);
        } catch(Exception e){ showError(e.getMessage()); }
    }

    private void openTable(ResultSet rs) throws IOException, SQLException {
      try{
          FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/TableDialog.fxml"));
          Parent p = loader.load();
          TableController tc = loader.getController();
          tc.setTableResultset(rs, "All Listings");

          Stage s = new Stage();
          s.initModality(Modality.APPLICATION_MODAL);
          s.setTitle("Listings");
          s.setScene(new Scene(p));
          s.show();
      } catch(Exception e){ showError(e.getMessage()); }
    }

    private void showInfo(String m){ new Alert(Alert.AlertType.INFORMATION,m).show(); }
    private void showError(String m){ new Alert(Alert.AlertType.ERROR,m).show(); }
}
