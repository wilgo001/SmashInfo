package com.example.smashinfo.game;

import android.widget.ImageButton;

import com.example.smashinfo.activity.FieldActivity;

public class Place {

    private ImageButton button;
    private boolean hasCard;
    private FieldActivity context;

    public Place(ImageButton button, FieldActivity context) {
        this.button = button;
        hasCard = false;
        this.context = context;
    }

    public boolean isEmpty() {
        return false;
    }

    public String isSelectable(){
        return "e";
    }

    public void selectCard(){

    }

    public FieldActivity getContext() {
        return context;
    }

    public void setContext(FieldActivity context) {
        this.context = context;
    }

    public void selectPlace(){

    }
}
