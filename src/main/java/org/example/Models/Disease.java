package org.example.Models;

public class Disease {
    public String name;
    public String full_name;

    public Disease(String name, String full_name) {
        this.name = name;
        this.full_name = full_name;
    }

    public String getName() {
        return name;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }
}
