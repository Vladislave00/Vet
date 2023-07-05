package org.example.Controllers;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.DBHandler;
import org.example.Models.User;

public class signupController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField addressField;

    @FXML
    private TextField loginField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField numberField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField passwordField1;

    @FXML
    private Button signInButton;

    @FXML
    private Label errorLaber;

    @FXML
    private Label errorLaber1;

    @FXML
    void initialize() {
        DBHandler.getConnection();

        signInButton.setOnAction(actionEvent -> {
            if (!passwordField.getText().equals(passwordField1.getText())){
                errorLaber1.setVisible(false);
                errorLaber.setVisible(true);
            } else {
                try {
                    if (DBHandler.exists(loginField.getText())) {
                        errorLaber.setVisible(false);
                        errorLaber1.setVisible(true);
                    } else {
                        signUpNewUser();

                        signInButton.getScene().getWindow().hide();

                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("/login.fxml"));
                        try {
                            fxmlLoader.load();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        Parent root = fxmlLoader.getRoot();
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.setTitle("MadCat");
                        stage.show();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void signUpNewUser() {

        try {
            DBHandler dbHandler = new DBHandler();
            User user = new User(loginField.getText(), passwordField.getText(), 1, nameField.getText(), addressField.getText(), numberField.getText());
            dbHandler.signup(user);
        } catch (SQLException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

}
