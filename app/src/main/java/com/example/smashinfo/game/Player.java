package com.example.smashinfo.game;

import com.example.smashinfo.activity.FieldActivity;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private String role;
    private int lifePoint;
    private FieldActivity field;
    private Deck deck;
    private PlaceSmasheur[] placesSmasheur;
    private PlaceEffect[] placesEffect;
    private List<Card> hand = new ArrayList<>();

    public Player(String role, int lifePoint, FieldActivity field){
        this.role = role;
        this.lifePoint = lifePoint;
        this.field = field;
        this.placesEffect = new PlaceEffect[4];
        this.placesSmasheur = new PlaceSmasheur[4];
        addPlace();
        this.deck = new Deck(field);
    }

    private void addPlace() {
        if (this.role.equals(field.nomHoster)) {
            placesSmasheur[0] = new PlaceSmasheur(field.C1, field);
            placesSmasheur[1] = new PlaceSmasheur(field.C2, field);
            placesSmasheur[2] = new PlaceSmasheur(field.C3, field);
            placesSmasheur[3] = new PlaceSmasheur(field.C4, field);
            placesEffect[0] = new PlaceEffect(field.D1, field);
            placesEffect[1] = new PlaceEffect(field.D2, field);
            placesEffect[2] = new PlaceEffect(field.D3, field);
            placesEffect[3] = new PlaceEffect(field.D4, field);
        }
        if (this.role.equals(field.nomJoiner)) {
            placesSmasheur[0] = new PlaceSmasheur(field.B1, field);
            placesSmasheur[1] = new PlaceSmasheur(field.B2, field);
            placesSmasheur[2] = new PlaceSmasheur(field.B3, field);
            placesSmasheur[3] = new PlaceSmasheur(field.B4, field);
            placesEffect[0] = new PlaceEffect(field.A1, field);
            placesEffect[1] = new PlaceEffect(field.A2, field);
            placesEffect[2] = new PlaceEffect(field.A3, field);
            placesEffect[3] = new PlaceEffect(field.A4, field);
        }
    }

    public void makeHand() {
        for(int i = 0; i < 4; i++) {
            hand.add(deck.drawCard());
        }
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public List<Card> getHand() {
        return hand;
    }
}
