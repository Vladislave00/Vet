package org.example.Models;

import java.util.ArrayList;

public class Owner {
    private String name;
    private String address;
    private String number;

    private ArrayList<Animal> pets;

    public static Owner OWNER;

    public Owner(String name, String address, String number) {
        this.name = name;
        this.address = address;
        this.number = number;
    }

    public ArrayList<Animal> getPets() {
        return pets;
    }

    public void setPets(ArrayList<Animal> pets) {
        this.pets = pets;
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
