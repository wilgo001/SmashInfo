package com.example.smashinfo.data;

public class DataCard {

    public String nom;
    public String description;
    public String probabilite;
    public int id;

    public DataCard() {
    }

    public DataCard(String nom, String description, String probabilite, int id) {
        this.nom = nom;
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

    @Override
    public String toString() {
        return "nom:" + nom +
                ", description:" + description +
                ", probabilite:" + probabilite;
    }


}
