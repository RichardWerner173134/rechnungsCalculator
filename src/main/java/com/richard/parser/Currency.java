package com.richard.parser;

public enum Currency {
    EUR(0, "Euro"),
    KC(1, "Tschechische Krone");

    private int id;
    private String name;

    private Currency(int id, String name){
        this.id = id;
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public int getId() {
        return id;
    }
}
