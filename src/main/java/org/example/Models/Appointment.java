package org.example.Models;

import java.sql.Date;
import java.sql.Time;

public class Appointment {
    private Doctor doctor;
    private Owner owner;
    private Animal animal;
    private Disease[] diseases;
    private Time time;
    private Date date;

    public Appointment(Doctor doctor, Owner owner, Animal animal, Disease[] diseases, Time time, Date date) {
        this.doctor = doctor;
        this.owner = owner;
        this.animal = animal;
        this.diseases = diseases;
        this.time = time;
        this.date = date;
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

    public Disease[] getDiseases() {
        return diseases;
    }

    public Time getTime() {
        return time;
    }

    public Date getDate() {
        return date;
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

    public void setDiseases(Disease[] diseases) {
        this.diseases = diseases;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}