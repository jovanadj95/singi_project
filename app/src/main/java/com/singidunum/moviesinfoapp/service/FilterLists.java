package com.singidunum.moviesinfoapp.service;

import com.singidunum.moviesinfoapp.model.filter.FilterObjectId;

import java.util.ArrayList;
import java.util.List;

public class FilterLists {

    public List<FilterObjectId> getGenres() {
        List<FilterObjectId> genres = new ArrayList<>();
        genres.add(new FilterObjectId("28", "Action"));
        genres.add(new FilterObjectId("12", "Adventure"));
        genres.add(new FilterObjectId("16", "Animation"));
        genres.add(new FilterObjectId("35", "Comedy"));
        genres.add(new FilterObjectId("80", "Crime"));
        genres.add(new FilterObjectId("99", "Documentary"));
        genres.add(new FilterObjectId("18", "Drama"));
        genres.add(new FilterObjectId("10751", "Family"));
        genres.add(new FilterObjectId("14", "Fantasy"));
        genres.add(new FilterObjectId("36", "History"));
        genres.add(new FilterObjectId("27", "Horror"));
        genres.add(new FilterObjectId("10402", "Music"));
        genres.add(new FilterObjectId("9648", "Mystery"));
        genres.add(new FilterObjectId("10749", "Romance"));
        genres.add(new FilterObjectId("878", "Science Fiction"));
        genres.add(new FilterObjectId("10770", "TV Movie"));
        genres.add(new FilterObjectId("53", "Thriller"));
        genres.add(new FilterObjectId("10752", "War"));
        genres.add(new FilterObjectId("37", "Western"));
        return genres;
    }

    public List<FilterObjectId> getProductions() {
        List<FilterObjectId> productions = new ArrayList<>();
        productions.add(new FilterObjectId("3036", "Walt Disney Studios Motion Pictures"));
        productions.add(new FilterObjectId("17", "Warner Bros. Entertainment"));
        productions.add(new FilterObjectId("5", "Columbia Pictures"));
        productions.add(new FilterObjectId("25", "20th Century Fox"));
        productions.add(new FilterObjectId("21", "Metro-Goldwyn-Mayer"));
        productions.add(new FilterObjectId("33", "Universal Pictures"));
        productions.add(new FilterObjectId("4", "Paramount"));
        productions.add(new FilterObjectId("1632", "Lionsgate"));
        productions.add(new FilterObjectId("7", "DreamWorks"));
        productions.add(new FilterObjectId("19551", "Marvel Enterprises"));
        return productions;
    }

    public List<FilterObjectId> getLanguages() {
        List<FilterObjectId> languages = new ArrayList<>();
        languages.add(new FilterObjectId("en", "English"));
        languages.add(new FilterObjectId("es", "Spanish"));
        languages.add(new FilterObjectId("fr", "French"));
        languages.add(new FilterObjectId("it", "Italian"));
        languages.add(new FilterObjectId("ru", "Russian"));
        languages.add(new FilterObjectId("sr", "Serbian"));
        languages.add(new FilterObjectId("el", "Greek"));
        languages.add(new FilterObjectId("hr", "Croatian"));
        languages.add(new FilterObjectId("de", "German"));
        return languages;
    }
}
