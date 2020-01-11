package com.example.smashinfo.game;

import com.example.smashinfo.activity.FieldActivity;
import com.example.smashinfo.data.DataCard;
import com.example.smashinfo.data.DataSmasheurCard;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Deck {

    private FieldActivity field;
    private List<Card> cards;

    public Deck(FieldActivity field) {
        this.field = field;
        this.cards = new ArrayList<>();
    }

    public void addCarte(DataSmasheurCard value, String key) {
        Card card = new CardSmasheur(value.getName(), value.getDescription(), key, PositionCard.DECK, Integer.parseInt(value.attaque), Integer.parseInt(value.defense), field);
    }

    public Card getTopCard() {
        Collections.shuffle(cards);
        return cards.remove(0);
    }

    public int getNbCards() {
        return cards.size();
    }
}
