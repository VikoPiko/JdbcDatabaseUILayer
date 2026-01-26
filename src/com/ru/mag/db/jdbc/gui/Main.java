package com.ru.mag.db.jdbc.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("mainApp.fxml"));
        primaryStage.setTitle("Real Estate Agency");
        primaryStage.show();
        primaryStage.setScene(new Scene(root, 520, 360));
    }


    public static void main(String[] args) {
        launch(args);
    }

}
