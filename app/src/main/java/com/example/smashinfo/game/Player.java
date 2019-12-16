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
        addPlace();
    }

    private void addPlace() {
        if (this.nom.equals(field.nomHoster)) {
            placesSmasheur[0] = new PlaceSmasheur(field.C1);
            placesSmasheur[1] = new PlaceSmasheur(field.C2);
            placesSmasheur[2] = new PlaceSmasheur(field.C3);
            placesSmasheur[3] = new PlaceSmasheur(field.C4);
            placesEffect[0] = new PlaceEffect(field.D1);
            placesEffect[1] = new PlaceEffect(field.D2);
            placesEffect[2] = new PlaceEffect(field.D3);
            placesEffect[3] = new PlaceEffect(field.D4);
        }
        if (this.nom.equals(field.nomJoiner)) {
            placesSmasheur[0] = new PlaceSmasheur(field.B1);
            placesSmasheur[1] = new PlaceSmasheur(field.B2);
            placesSmasheur[2] = new PlaceSmasheur(field.B3);
            placesSmasheur[3] = new PlaceSmasheur(field.B4);
            placesEffect[0] = new PlaceEffect(field.A1);
            placesEffect[1] = new PlaceEffect(field.A2);
            placesEffect[2] = new PlaceEffect(field.A3);
            placesEffect[3] = new PlaceEffect(field.A4);
        }
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
