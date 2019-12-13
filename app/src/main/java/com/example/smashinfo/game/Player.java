package com.example.smashinfo.game;

import com.example.smashinfo.activity.FieldActivity;

public class Player {

    private String nom;
    private int lifePoint;
    private FieldActivity field;
    private Deck deck;
    private PlaceSmasheur[] placesSmasheur;
    private PlaceEffect[] placesEffect;

    public Player(String nom, int lifePoint, FieldActivity field){
        this.nom = nom;
        this.lifePoint = lifePoint;
        this.field = field;
        this.placesEffect = new PlaceEffect[4];
        this.placesSmasheur = new PlaceSmasheur[4];
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getLifePoint() {
        return lifePoint;
    }

    public void setLifePoint(int lifePoint) {
        this.lifePoint = lifePoint;
    }

    public FieldActivity getField() {
        return field;
    }

    public void setField(FieldActivity field) {
        this.field = field;
    }
}
