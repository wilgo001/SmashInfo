package com.example.smashinfo.data;

public class DataCard {

    public String name;
    public String description;

    public DataCard() {
    }

    public DataCard(String name, String description, String valAttaque, String valDefense) {
        this.name = name;
        this.description = description;
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
        return "name:" + name +
                ", description:" + description;
    }
}
