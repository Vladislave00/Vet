package org.example.Controllers;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;
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
import org.example.Models.*;

public class UserController {

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
    private Button backD;

    @FXML
    private Button backN;

    @FXML
    private Button backP;

    @FXML
    private Button addPet;

    @FXML
    private TextField addPetBreedF;

    @FXML
    private TextField addPetNameF;

    @FXML
    private AnchorPane addPetPane;

    @FXML
    private Button backAddPet;

    @FXML
    private TextField AppTime;

    @FXML
    private TextField AppDocName;

    @FXML
    private TextField AppPetName;

    @FXML
    private Button backA;

    @FXML
    private AnchorPane makeAppointmentPane;

    @FXML
    private Button submitAddPet;

    @FXML
    private Button regAppointment;

    @FXML
    private Button submitAppointment;
    @FXML
    void initialize() {
        changePasswordButton.setOnAction(actionEvent -> {
            changePasswordPane.setVisible(true);
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
        changePasswordSubmit.setOnAction(actionEvent ->{
            String old = oldPassword.getText();
            String newP = nawPassword.getText();

            try {
                if (Objects.equals(DBHandler.passwordHashing(old), User.USER.getPassword())){
                    DBHandler dbHandler = new DBHandler();
                    try {
                        dbHandler.changePassword(User.USER, newP);
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
                    DBHandler dbHandler = new DBHandler();
                    try {
                        dbHandler.changeName(User.USER, nick);
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
                    DBHandler dbHandler = new DBHandler();
                    try {
                        dbHandler.changeData(User.USER, newname, newAddr, newNum);
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

        addPet.setOnAction(actionEvent -> {
            addPetPane.setVisible(true);
        });

        backAddPet.setOnAction(actionEvent -> {
            addPetPane.setVisible(false);
        });

        submitAddPet.setOnAction(actionEvent -> {
            String name = addPetNameF.getText();
            Breed breed = new Breed(addPetBreedF.getText());
            Owner owner = new Owner(User.USER.getName(), User.USER.getAddress(), User.USER.getNumber());
            Animal pet = new Animal(name, breed, owner);
            DBHandler dbHandler = new DBHandler();
            try {
                dbHandler.addPet(pet);
                addPetPane.setVisible(false);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        regAppointment.setOnAction(actionEvent -> {
            makeAppointmentPane.setVisible(true);
        });

        backA.setOnAction(actionEvent -> {
            makeAppointmentPane.setVisible(false);
        });

        submitAppointment.setOnAction(actionEvent -> {
            String docname = AppDocName.getText();
            String date = AppTime.getText();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime datetime = LocalDateTime.parse(date, formatter);
            String petName = AppPetName.getText();
            DBHandler dbHandler = new DBHandler();
            try {
                dbHandler.makeAppointment(docname, petName, datetime);
                addPetPane.setVisible(false);
            } catch (SQLException e) {
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
