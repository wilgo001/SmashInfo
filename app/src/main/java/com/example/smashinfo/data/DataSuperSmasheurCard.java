package com.example.smashinfo.data;

public class DataSuperSmasheurCard extends DataSmasheurCard {

    public String sacrifice;

    public DataSuperSmasheurCard(String name, String description, String valAttaque, String valDefense, String attaque, String defense, String groupe1, String groupe2, String sacrifice) {
        super(name, description, valAttaque, valDefense, attaque, defense, groupe1, groupe2);
        this.sacrifice = sacrifice;
    }

    public String getSacrifice() {
        return sacrifice;
    }

    public void setSacrifice(String sacrifice) {
        this.sacrifice = sacrifice;
    }

    @Override
    public String toString() {
        return super.toString() +
                "sacrifice:" + sacrifice;
    }
}
