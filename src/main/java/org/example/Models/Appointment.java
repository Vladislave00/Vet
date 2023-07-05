package org.example.Models;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Appointment {
    private Doctor doctor;
    private Owner owner;
    private Animal animal;
    private ArrayList<Disease> diseases;

    private LocalDateTime dateTime;

    public Appointment(Doctor doctor, Owner owner, Animal animal, LocalDateTime dateTime) {
        this.doctor = doctor;
        this.owner = owner;
        this.animal = animal;
        this.dateTime = dateTime;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Owner getOwner() {
        return owner;
    }

    public Animal getAnimal() {
        return animal;
    }

    public ArrayList<Disease> getDiseases() {
        return diseases;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDiseases(ArrayList<Disease> diseases) {
        this.diseases = diseases;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
