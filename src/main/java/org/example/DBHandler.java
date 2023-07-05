package org.example;

import org.example.Models.Animal;
import org.example.Models.Owner;
import org.example.Models.User;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.*;

public class DBHandler {
    private static Connection connection;
    private static final String DB_URL = "jdbc:mysql://std-mysql.ist.mospolytech.ru:3306/std_2294_kurs";
    private static final String DB_USER = "std_2294_kurs";
    private static final String DB_PASSWORD = "vladikkk848";
    public DBHandler() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        }
        catch (ClassNotFoundException e) {
            System.out.println("JDBC не найден");
        } catch (SQLException e) {
            System.out.println("Ошибка при подключении к базе данных: " + e.getMessage());
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Ошибка закрытия подключения к базе данных: " + e.getMessage());
        }
    }

    public void signup(User user) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
        if (exists(user.getUsername())) return;
        String insert = "INSERT INTO Users(username, password, role, name, address, number) VALUES ('" + user.getUsername() + "','" + passwordHashing(user.getPassword()) + "','" + user.getRole() + "','" + user.getName() + "','" + user.getAddress() + "','" + user.getNumber() + "')";
        PreparedStatement prst = connection.prepareStatement(insert);
        prst.executeUpdate();
    }

    public ResultSet getUser(User user) throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException {
        ResultSet rs;
        String select = "SELECT * FROM Users WHERE username = '" + user.getUsername() + "' AND password = '" + user.getPassword() + "';";
        PreparedStatement prst = connection.prepareStatement(select);
        rs = prst.executeQuery();
        return rs;
    }

    public void changeName(User user, String newName) throws SQLException {
        String username = user.getUsername();
        String password = user.getPassword();
        String update = "UPDATE Users SET username = '" + newName + "' WHERE username = '" + username + "' AND password = '" + password + "';";
        PreparedStatement prst = connection.prepareStatement(update);
        prst.executeUpdate();
    }

    public void changeData(User user, String newName, String newAddr, String newPhone) throws SQLException {
        String username = user.getUsername();
        String password = user.getPassword();
        String update = "UPDATE Users SET name = '" + newName + "', address = '" + newAddr + "', number = '" + newPhone + "' WHERE username = '" + username + "' AND password = '" + password + "';";
        PreparedStatement prst = connection.prepareStatement(update);
        prst.executeUpdate();
    }
    public void changePassword(User user, String newPassword) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
        String username = user.getUsername();
        String password = user.getPassword();
        newPassword = passwordHashing(newPassword);
        String update = "UPDATE Users SET password = '" + newPassword + "' WHERE username = '" + username + "' AND password = '" + password + "';";
        PreparedStatement prst = connection.prepareStatement(update);
        prst.executeUpdate();
    }

    public static String passwordHashing(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = factory.generateSecret(spec).getEncoded();
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : hash) {
            stringBuilder.append(b);
        }
        return stringBuilder.toString();
    }

    public void addPet(Animal pet) throws SQLException {
        if (!existsOwner(Owner.OWNER)){
            String insert1 = "INSERT INTO Owner(name, address, number) VALUES ('" + Owner.OWNER.getName() + "','" + Owner.OWNER.getAddress() + "','" + Owner.OWNER.getNumber() + "')";
            PreparedStatement prst1 = connection.prepareStatement(insert1);
            prst1.executeUpdate();
        }
        if (!existsBreed(pet.getBreed().getName())){
            String insert2 = "INSERT INTO Breed(name) VALUES ('" + pet.getBreed().getName() + "')";
            PreparedStatement prst2 = connection.prepareStatement(insert2);
            prst2.executeUpdate();
        }
        Statement s3 = connection.createStatement();
        String select2 = "SELECT id FROM Breed WHERE name = '" + pet.getBreed().getName() + "';";
        ResultSet rs3 = s3.executeQuery(select2);
        rs3.next();
        int breedId = rs3.getInt("id");
        String insert = "INSERT INTO Animal(name, id_breed) VALUES ('" + pet.getName() + "','" + breedId + "')";
        PreparedStatement prst = connection.prepareStatement(insert);
        prst.executeUpdate();
        Statement s = connection.createStatement();
        Statement s1 = connection.createStatement();
        Statement s2 = connection.createStatement();
        String select = "SELECT id FROM Owner WHERE name = '" + Owner.OWNER.getName() + "';";
        String select1 = "SELECT id FROM Animal WHERE name = '" + pet.getName() + "';";
        ResultSet rs1 = s.executeQuery(select);
        ResultSet rs2 = s1.executeQuery(select1);
        rs1.next();
        rs2.next();
        int ownerId = rs1.getInt("id");
        int petId = rs2.getInt("id");
        String insert1 = "INSERT INTO animal_owner(id_owner, id_animal) VALUES ('" + ownerId+ "','" + petId + "')";
        s2.executeUpdate(insert1);
        String select3 = "SELECT id FROM animal_owner WHERE id_owner = '" + ownerId + "' AND id_animal = '" + petId + "';";
        Statement s4 = connection.createStatement();
        ResultSet rs4 = s4.executeQuery(select3);
        rs4.next();
        int ownshipId = rs4.getInt("id");
        String update1 = "UPDATE Animal SET ownship_id = '" + ownshipId+ "' WHERE id = '" + petId + "';";
        Statement s6 = connection.createStatement();
        s6.executeUpdate(update1);
    }
    public static boolean exists(String username) throws SQLException {
        Statement statement = connection.createStatement();
        String query = "SELECT username, password, role FROM Users WHERE username = '" + username + "';";
        ResultSet result = statement.executeQuery(query);
        return result.next();
    }

    public static boolean existsOwner(Owner owner) throws SQLException {
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM Owner WHERE name = '" + owner.getName() + "';";
        ResultSet result = statement.executeQuery(query);
        return result.next();
    }
    public static boolean existsBreed(String breed) throws SQLException {
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM Breed WHERE name = '" + breed + "';";
        ResultSet result = statement.executeQuery(query);
        return result.next();
    }
}
