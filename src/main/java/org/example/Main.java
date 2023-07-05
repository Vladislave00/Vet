package org.example;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.sql.SQLException;


public class Main extends Application{

    public static void main(String[] args) throws SQLException {
        DBHandler.dbHandler = new DBHandler();
        launch(args);
        DBHandler.dbHandler.closeConnection();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("MadCat");

        stage.show();
    }
}