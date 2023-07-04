package org.example.Controllers;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.DBHandler;
import org.example.Models.User;

public class Controller {


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField loginField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button signInButton;

    @FXML
    private Button signUpButton;

    @FXML
    void initialize() {
        signUpButton.setOnAction(actionEvent -> {
            changeScene("/signup.fxml");
        });

        signInButton.setOnAction(actionEvent ->{
            String username = loginField.getText().trim();
            String password = passwordField.getText().trim();


            try {
                login(username, password);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            } catch (InvalidKeySpecException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        });
    }

    private void login(String username, String password) throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException {
        DBHandler dbHandler = new DBHandler();
        User user = new User();
        user.setUsername(username);
        user.setPassword(DBHandler.passwordHashing(password));
        ResultSet rs = dbHandler.getUser(user);
        int role = Integer.parseInt(rs.getString(4));
        int cnt = 0;
        while (rs.next()) {
            cnt++;
        }
        switch (role){
            case 1:
                if (cnt == 1) {
                    changeScene("/user.fxml");
                }
            case 2:
                if (cnt == 1) {
                    changeScene("/doc.fxml");
                }
        }

        if (cnt == 1) {
            changeScene("/user.fxml");
        }
    }
    public void changeScene(String path){
        signInButton.getScene().getWindow().hide();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(path));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Parent root = fxmlLoader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }
}
