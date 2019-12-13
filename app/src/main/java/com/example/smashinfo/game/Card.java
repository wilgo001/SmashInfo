package com.example.smashinfo.game;

public class Card {

    private String name, cardKey;
    private int id;
    private String description;

    public void Card(int id, String name, String description, String cardKey, PositionCard positionCard ){
        this.id = id;
        this.name = name;
        this.description = description;
        this.cardKey = cardKey;
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
