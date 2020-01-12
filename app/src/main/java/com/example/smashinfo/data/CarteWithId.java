package com.example.smashinfo.data;

public class CarteWithId {

    private String key;
    private DataCard value;

    public CarteWithId(String key, DataCard value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public DataCard getValue() {
        return value;
    }

    public void setValue(DataCard value) {
        this.value = value;
    }
}
