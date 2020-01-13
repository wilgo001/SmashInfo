package com.example.smashinfo.data;

public class DataSmasheurCard extends  DataCard{


    public String
            attaque,
            defense,
            groupe1,
            groupe2;

    public DataSmasheurCard() {
        super();
    }

    public DataSmasheurCard(String nom, String description, String probabilite, String attaque, String defense, String groupe1, String groupe2, int id) {
        super(nom, description, probabilite, id);
        this.attaque = attaque;
        this.defense = defense;
        this.groupe1 = groupe1;
        this.groupe2 = groupe2;
    }

    public String toString() {
        return super.toString() +
                ", attaque:" + attaque +
                ", defense:" + defense +
                ", groupe1:" + groupe1 +
                ", groupe2:" + groupe2 ;
    }

    public String getAttaque() {
        return attaque;
    }

    public void setAttaque(String attaque) {
        this.attaque = attaque;
    }

    public String getDefense() {
        return defense;
    }

    public void setDefense(String defense) {
        this.defense = defense;
    }
}
