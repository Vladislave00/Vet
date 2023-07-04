package org.example;

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
        String insert = "INSERT INTO Users(username, password, role, name, address, number) VALUES ('" + user.getUsername() + "','" + passwordHashing(user.getPassword()) + "', 1,'" + user.getName() + "','" + user.getAddress() + "','" + user.getNumber() + "')";
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

    public boolean exists(String username) throws SQLException {
        Statement statement = connection.createStatement();
        String query = "SELECT username, password, role FROM Users WHERE username = '" + username + "';";
        ResultSet result = statement.executeQuery(query);
        return result.next();
    }


}
