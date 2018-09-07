package com.singidunum.moviesinfoapp.model.filter;

public class FilterObject {

    private String id, displayName;

    public FilterObject(String id, String displayName) {
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
