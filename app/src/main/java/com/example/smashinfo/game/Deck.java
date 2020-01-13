package com.example.smashinfo.game;

import com.example.smashinfo.activity.FieldActivity;
import com.example.smashinfo.data.DataCard;
import com.example.smashinfo.data.DataSmasheurCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

    private FieldActivity field;
    private List<Card> cards;

    public Deck(FieldActivity field) {
        this.field = field;
        this.cards = new ArrayList<>();
    }

    public void addCarte(DataSmasheurCard value, String key, String imageName) {
        Card card = new CardSmasheur(value.getName(), value.getDescription(), key, PositionCard.DECK, Integer.parseInt(value.attaque), Integer.parseInt(value.defense), field, value.groupe1, imageName);
        cards.add(card);
    }

    public static CardSmasheur convert(DataSmasheurCard card, String key, FieldActivity field) {
       return new CardSmasheur(card.getName(), card.getDescription(), key, PositionCard.DECK, Integer.parseInt(card.attaque), Integer.parseInt(card.defense), field, card.groupe1, Integer.toString(card.getId()));
    }

    public Card drawCard() {
        Collections.shuffle(cards);
        return cards.remove(0);
    }

    public Card getTopCard() {
        return cards.get(cards.size()-1);
    }

    public int getNbCards() {
        if(cards.size() < 1) {
            return 0;
        }
        return cards.size();
    }
}
