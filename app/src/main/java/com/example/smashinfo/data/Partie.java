package com.example.smashinfo.data;

import androidx.annotation.NonNull;

public class Partie {

    public String hosterName;
    public String joinerName;
    public String color;

    public Partie() {
    }

    public Partie(String hosterName, String joinerName, String color) {
        this.hosterName = hosterName;
        this.joinerName = joinerName;
        this.color = color;
    }

    public String getHosterName() {
        return hosterName;
    }

    public String getJoinerName() {
        return joinerName;
    }

    public String getColor() {
        return color;
    }

    public void setHosterName(String hosterName) {
        this.hosterName = hosterName;
    }

    public void setJoinerName(String joinerName) {
        this.joinerName = joinerName;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @NonNull
    @Override
    public String toString() {
        return "hoster : " + hosterName
                + "\njoiner : " + joinerName
                + "\ncolor : " + color;
    }
}
