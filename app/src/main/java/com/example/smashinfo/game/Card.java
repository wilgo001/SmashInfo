package com.example.smashinfo.game;

public class Card {

    private String name, cardKey;
    private int id;
    private String description;
    private PositionCard positionCard;

    public Card(String name, String description, String cardKey, PositionCard positionCard ){
        this.id = id;
        this.name = name;
        this.description = description;
        this.cardKey = cardKey;
        this.positionCard = positionCard;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PositionCard getPositionCard() {
        return positionCard;
    }

    public void setPositionCard(PositionCard positionCard) {
        this.positionCard = positionCard;
    }

    @Override
    public String toString() {
        return "Card{" +
                "name='" + name + '\'' +
                ", cardKey='" + cardKey + '\'' +
                ", id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
