package com.example.smashinfo.data;

public class DataSmasheurCard extends DataCard {

    public String attaque,
                defense,
                groupe1,
                groupe2;

    public DataSmasheurCard() {
        super();
    }

    public DataSmasheurCard(String name, String description, String valAttaque, String valDefense, String attaque, String defense, String groupe1, String groupe2) {
        super(name, description, valAttaque, valDefense);
        this.attaque = attaque;
        this.defense = defense;
        this.groupe1 = groupe1;
        this.groupe2 = groupe2;
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

    public String getGroupe1() {
        return groupe1;
    }

    public void setGroupe1(String groupe1) {
        this.groupe1 = groupe1;
    }

    public String getGroupe2() {
        return groupe2;
    }

    public void setGroupe2(String groupe2) {
        this.groupe2 = groupe2;
    }

    @Override
    public String toString() {
        return super.toString() +
                "attaque:" + attaque +
                ", defense:" + defense +
                ", groupe1" + groupe1 +
                ", groupe2:" + groupe2;
    }
}
