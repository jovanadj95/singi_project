package com.singidunum.moviesinfoapp;

public class FilterObjectId {

    private String id, displayName;

    public FilterObjectId(String id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getId() {
        return id;
    }
}
