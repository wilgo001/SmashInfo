package com.example.smashinfo.data;

public class DataCard {

    public String name;
    public String description;
    public String valAttaque;
    public String valDefense;

    public DataCard() {
    }

    public DataCard(String name, String description, String valAttaque, String valDefense) {
        this.name = name;
        this.description = description;
        this.valAttaque = valAttaque;
        this.valDefense = valDefense;
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

    public String getValAttaque() {
        return valAttaque;
    }

    public void setValAttaque(String valAttaque) {
        this.valAttaque = valAttaque;
    }

    public String getValDefense() {
        return valDefense;
    }

    public void setValDefense(String valDefense) {
        this.valDefense = valDefense;
    }
}
