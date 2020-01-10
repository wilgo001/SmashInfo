package com.example.smashinfo.game;

import android.widget.ImageButton;
import android.widget.Toast;

import com.example.smashinfo.activity.FieldActivity;

public class PlaceSmasheur extends Place {

    private CardSmasheur card;

    public PlaceSmasheur(ImageButton button, FieldActivity context) {
        super(button, context);
    }

    public CardSmasheur getCard() {
        if (card == null) {
            Toast.makeText(this.getContext().getApplicationContext(), "il n'y a pas de card sur cet emplacement", Toast.LENGTH_SHORT);
        }
        return card;
    }

    public void setCard(CardSmasheur card) {
        this.card = card;
    }
}
