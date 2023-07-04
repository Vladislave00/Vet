package org.example.Models;

public class Animal {

    private String name;
    private Breed breed;
    private Owner owner;

    public Animal(String name, Breed breed, Owner owner) {
        this.name = name;
        this.breed = breed;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public Breed getBreed() {
        return breed;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBreed(Breed breed) {
        this.breed = breed;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}
