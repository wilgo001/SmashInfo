package com.example.smashinfo.data;

public class DataCard {

    public String nom;
    public String description;
    public String probabilite;

    public DataCard() {
    }

    public DataCard(String nom, String description, String probabilite) {
        this.nom = nom;
        this.description = description;
        this.probabilite = probabilite;
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

    @Override
    public String toString() {
        return "nom:" + nom +
                ", description:" + description +
                ", probabilite:" + probabilite;
    }
}
