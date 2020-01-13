package com.example.smashinfo.data;

public class DataCard {

    public String nom;
    public String description;
    public String probabilite;
    public int id;

    public DataCard() {
    }

    public DataCard(String name, String description, String probabilite, int id) {
        this.nom = name;
        this.description = description;
        this.probabilite = probabilite;
        this.id = id;
    }

    public String getName() {
        return nom;
    }

    public void setName(String nom) {
        this.nom = nom;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "name:" + nom +
                ", description:" + description +
                ", probabilite:" + probabilite;
    }


}
