package com.example.smashinfo.game;

import android.widget.ImageButton;

public class place {

    private ImageButton button;
    private boolean hasCard;

    public place(ImageButton button) {
        this.button = button;
        hasCard = false;
    }

    public boolean isEmpty() {
        return false;
    }

    public String isSelectable(){
        return "e";
    }

    public void selectCard(){

    }

    public void selectPlace(){

    }
}
