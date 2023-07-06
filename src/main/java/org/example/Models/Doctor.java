package org.example.Models;

public class Doctor {
    private String name;
    private String address;
    private String number;
    public static Doctor DOC;

    public Doctor(String name, String address, String number) {
        this.name = name;
        this.address = address;
        this.number = number;
    }

    public Doctor() {
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
