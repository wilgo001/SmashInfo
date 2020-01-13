package com.example.smashinfo.game;

import com.example.smashinfo.data.DataCard;
import com.example.smashinfo.data.DataSmasheurCard;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Card {

    private String name, cardKey;
    private String id;
    private String description;
    private PositionCard positionCard;
    private StorageReference imageRef;

    public Card(String name, String description, String cardKey, PositionCard positionCard, String nomImage ){
        this.id = nomImage;
        this.name = name;
        this.description = description;
        this.cardKey = cardKey;
        this.positionCard = positionCard;
        this.imageRef = FirebaseStorage.getInstance().getReference().child("cartes/" + this.cardKey + "/" + nomImage);
    }

    public String getCardKey() {
        return cardKey;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public void setCardKey(String cardKey) {
        this.cardKey = cardKey;
    }

    public StorageReference getImageRef() {
        return imageRef;
    }

    public void setImageRef(StorageReference imageRef) {
        this.imageRef = imageRef;
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
