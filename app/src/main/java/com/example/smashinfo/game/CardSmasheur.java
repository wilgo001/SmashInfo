package com.example.smashinfo.game;

import com.example.smashinfo.activity.FieldActivity;

public class CardSmasheur extends Card {

    private int valAttack;
    private int valDefense;
    private FieldActivity context;
    private String groupe1;

    public CardSmasheur(String name, String description, String cardKey, PositionCard positionCard, int valAttack, int valDefense, FieldActivity context, String groupe1, String imageName) {
        super(name, description, cardKey, positionCard, imageName);
        this.valAttack = valAttack;
        this.valDefense = valDefense;
        this.context = context;
        this.groupe1 = groupe1;
    }

    public void Attack(){

    }

    public String getGroupe1() {
        return groupe1;
    }

    public void setGroupe1(String groupe1) {
        this.groupe1 = groupe1;
    }

    public int getValAttack() {
        return valAttack;
    }

    public void setValAttack(int valAttack) {
        this.valAttack = valAttack;
    }

    public int getValDefense() {
        return valDefense;
    }

    public void setValDefense(int valDefense) {
        this.valDefense = valDefense;
    }

    public void Attack(PlaceSmasheur cible) {
        double difference = this.getValAttack();
        difference -= cible.getCard().getValAttack();
        if (difference == 0) {
            cible.getCard().setPositionCard(PositionCard.CIMETIERRE);
            this.setPositionCard(PositionCard.CIMETIERRE);
        }else if (difference > 0) {
            cible.getCard().setPositionCard(PositionCard.CIMETIERRE);
        }
    }


}
