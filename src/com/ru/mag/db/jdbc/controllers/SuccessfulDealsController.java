package com.ru.mag.db.jdbc.controllers;

import com.ru.mag.db.jdbc.queries.SuccessfulDealsQueries;
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

public class SuccessfulDealsController {
    @FXML private TextField propertyId;
    @FXML
    private TextField finalPrice;
    @FXML private TextField agentId;
    @FXML private TextField clientId;

    SuccessfulDealsQueries repo = new SuccessfulDealsQueries();

    public void createDeal(){
        try{
            repo.createDeal(
                    Integer.parseInt(propertyId.getText()),
                    Double.parseDouble(finalPrice.getText()),
                    Integer.parseInt(agentId.getText()),
                    Integer.parseInt(clientId.getText())
            );
            showInfo("Deal saved");
        } catch(Exception e){ showError(e.getMessage()); }
    }

    public void getAllDeals(){
        try{
            openTable(repo.getAllDeals(), "All Deals");
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
