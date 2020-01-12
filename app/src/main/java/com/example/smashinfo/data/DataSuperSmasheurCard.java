package com.example.smashinfo.data;

public class DataSuperSmasheurCard extends DataSmasheurCard {

    public String sacrifice;

    public DataSuperSmasheurCard(String name, String description, String probabilite, String attaque, String defense, String groupe1, String groupe2, String sacrifice, int id) {
        super(name, description, probabilite, attaque, defense, groupe1, groupe2, id);
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
