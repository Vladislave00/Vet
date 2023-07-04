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
            } catch (NoSuchAlgorithmException | InvalidKeySpecException | SQLException e) {
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
        rs.next();
        User user1 = new User(rs.getString(2),rs.getString(3),Integer.parseInt(rs.getString(4)),rs.getString(5),rs.getString(6),rs.getString(7));
        int role = Integer.parseInt(rs.getString(4));
        User.login(user1);
        if (role == 1) changeScene("/user.fxml");
        else if (role == 2) changeScene("/doc.fxml");
        else if (role == 3) changeScene("/admin.fxml");
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
        stage.show();
    }
}
