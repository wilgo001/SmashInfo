package com.example.smashinfo.data;

public class DataCard {

    public String name;
    public String description;
    public String probabilite;
    public String id;

    public DataCard() {
    }

    public DataCard(String name, String description, String probabilite, String id) {
        this.name = name;
        this.description = description;
        this.probabilite = probabilite;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String nom) {
        this.name = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProbabilite() {
        return probabilite;
    }

    public void setProbabilite(String probabilite) {
        this.probabilite = probabilite;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "name:" + name +
                ", description:" + description +
                ", probabilite:" + probabilite;
    }


}
