package com.example.smashinfo.game;

import com.example.smashinfo.activity.FieldActivity;
import com.google.firebase.database.DatabaseReference;

public class Deck {

    private FieldActivity field;
    private Card[] cards;

    public Deck(FieldActivity field, DatabaseReference refDeck) {
        this.field = field;
        this.cards = new Card[30];
    }
}
