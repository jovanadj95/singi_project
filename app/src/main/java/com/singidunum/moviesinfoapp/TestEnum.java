package com.singidunum.moviesinfoapp;

public enum TestEnum {
    ACTION("28"),
    ADVENTURE("12");

    private String order;

    TestEnum(String order) {
        this.order = order;
    }

    public String getOrder() {
        return order;
    }
}
