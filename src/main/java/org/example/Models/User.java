package org.example.Models;

public class User {
    private String username;
    private String password;
    private int role;
    private String name;
    private String address;
    private String number;

    public User(String username, String password, int role, String name, String address, String number) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.name = name;
        this.address = address;
        this.number = number;
    }

    public User() {

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getNumber() {
        return number;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
