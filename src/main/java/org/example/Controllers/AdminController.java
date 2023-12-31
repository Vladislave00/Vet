package org.example.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.DBHandler;
import org.example.Models.User;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdminController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MenuItem changeDataButton;

    @FXML
    private AnchorPane changeDataPane;

    @FXML
    private Button changeDataSubmit;

    @FXML
    private MenuItem changeNameButton;

    @FXML
    private AnchorPane changeNickPane;

    @FXML
    private MenuItem changePasswordButton;

    @FXML
    private AnchorPane changePasswordPane;

    @FXML
    private Button changePasswordSubmit;

    @FXML
    private Button changeUsernameSubmit;

    @FXML
    private MenuItem exitButton;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private TextField nawPassword;

    @FXML
    private TextField newAddress;

    @FXML
    private TextField newName;

    @FXML
    private TextField newPhone;

    @FXML
    private TextField newUsername;

    @FXML
    private TextField oldPassword;

    @FXML
    private TextField passwordData;

    @FXML
    private TextField passwordUsername;

    @FXML
    private MenuItem showProfileButton;

    @FXML
    private Label nameLabel;

    @FXML
    private Label nickLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label addrLabel;

    @FXML
    private AnchorPane profilePane;

    @FXML
    private Button backButton;

    @FXML
    private Label roleLabel;

    @FXML
    private TextField addrF;

    @FXML
    private TextField nameF;

    @FXML
    private TextField nickF;

    @FXML
    private TextField passwordF;

    @FXML
    private TextField phoneF;

    @FXML
    private Button regDocButton;

    @FXML
    private AnchorPane regDocPane;

    @FXML
    private Button backButtonReg;

    @FXML
    private Button submitDocButton;

    @FXML
    private Button backD;

    @FXML
    private Button backN;

    @FXML
    private Button backP;

    @FXML
    void initialize() {
        changePasswordButton.setOnAction(actionEvent -> {
            changePasswordPane.setVisible(true);
        });
        changePasswordSubmit.setOnAction(actionEvent ->{
            String old = oldPassword.getText();
            String newP = nawPassword.getText();

            try {
                if (Objects.equals(DBHandler.passwordHashing(old), User.USER.getPassword())){
                    try {
                        DBHandler.dbHandler.changePassword(User.USER, newP);
                        User.USER.setPassword(newP);
                    } catch (SQLException | NoSuchAlgorithmException | InvalidKeySpecException e) {
                        throw new RuntimeException(e);
                    }
                    changePasswordPane.setVisible(false);
                }
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                throw new RuntimeException(e);
            }
        });

        changeNameButton.setOnAction(actionEvent -> {
            changeNickPane.setVisible(true);
        });
        changeUsernameSubmit.setOnAction(actionEvent ->{
            String p = passwordUsername.getText();
            String nick = newUsername.getText();

            try {
                if (Objects.equals(DBHandler.passwordHashing(p), User.USER.getPassword())){
                    try {
                        DBHandler.dbHandler.changeName(User.USER, nick);
                        User.USER.setUsername(nick);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    changeNickPane.setVisible(false);
                }
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                throw new RuntimeException(e);
            }
        });

        changeDataButton.setOnAction(actionEvent -> {
            changeDataPane.setVisible(true);
        });

        changeDataSubmit.setOnAction(actionEvent -> {
            String newname = newName.getText();
            String newAddr = newAddress.getText();
            String newNum = newPhone.getText();
            String p = passwordData.getText();
            try {
                if (Objects.equals(DBHandler.passwordHashing(p), User.USER.getPassword())){
                    try {
                        DBHandler.dbHandler.changeData(User.USER, newname, newAddr, newNum);
                        User.USER.setName(newname);
                        User.USER.setAddress(newAddr);
                        User.USER.setNumber(newNum);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    changeDataPane.setVisible(false);
                }
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                throw new RuntimeException(e);
            }
        });

        exitButton.setOnAction(actionEvent -> {
            changeScene("/login.fxml");
        });

        showProfileButton.setOnAction(actionEvent -> {
            profilePane.setVisible(true);
            nickLabel.setText(User.USER.getUsername());
            nameLabel.setText(User.USER.getName());
            addrLabel.setText(User.USER.getAddress());
            phoneLabel.setText(User.USER.getNumber());
            int role = User.USER.getRole();
            if (role == 1) roleLabel.setText("Роль: Пользователь");
            else if (role == 2) roleLabel.setText("Роль: Врач");
            else if (role == 3) roleLabel.setText("Роль: Админ");
        });

        backButton.setOnAction(actionEvent -> {
            profilePane.setVisible(false);
        });

        regDocButton.setOnAction(actionEvent -> {
            regDocPane.setVisible(true);
        });

        backButtonReg.setOnAction(actionEvent -> {
            regDocPane.setVisible(false);
        });

        backP.setOnAction(actionEvent -> {
            changePasswordPane.setVisible(false);
        });
        backD.setOnAction(actionEvent -> {
            changeDataPane.setVisible(false);
        });
        backN.setOnAction(actionEvent -> {
            changeNickPane.setVisible(false);
        });

        submitDocButton.setOnAction(actionEvent -> {
            String nick = nickF.getText();
            String password = passwordF.getText();
            String name = nameF.getText();
            String addr = addrF.getText();
            String phone = phoneF.getText();

            try {
                User user = new User(nick, password, 2, name, addr, phone);
                DBHandler.dbHandler.signup(user);
                DBHandler.dbHandler.addDoctor(user);
                regDocPane.setVisible(false);
            } catch (SQLException | NoSuchAlgorithmException | InvalidKeySpecException e) {
                throw new RuntimeException(e);
            }
        });


    }
    public void changeScene(String path){
        mainPane.getScene().getWindow().hide();

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
        stage.setTitle("MadCat");
        stage.show();
    }
}
