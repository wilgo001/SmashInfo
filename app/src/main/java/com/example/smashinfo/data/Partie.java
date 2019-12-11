package com.example.smashinfo.data;

import androidx.annotation.NonNull;

public class Partie {

    public String hosterName;
    public String joinerName;
    public String color;
    public boolean start;
    public boolean hosterReady;
    public boolean joinerReady;

    public Partie() {
    }

    public Partie(String hosterName, String joinerName, String color, boolean start, boolean hosterReady, boolean joinerReady) {
        this.hosterName = hosterName;
        this.joinerName = joinerName;
        this.color = color;
        this.start = start;
        this.hosterReady = hosterReady;
        this.joinerReady = joinerReady;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
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
