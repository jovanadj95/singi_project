package com.singidunum.moviesinfoapp;

import java.util.HashMap;

public class FilterObject {
    private HashMap<String, String> options;
    private String filterName;

    public FilterObject(HashMap<String, String> options, String filterName) {
        this.options = options;
        this.filterName = filterName;
    }

    public HashMap<String, String> getOptions() {
        return options;
    }

    public void setOptions(HashMap<String, String> options) {
        this.options = options;
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }
}
