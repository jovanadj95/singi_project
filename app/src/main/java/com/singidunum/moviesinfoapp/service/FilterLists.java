package com.singidunum.moviesinfoapp.service;

import com.singidunum.moviesinfoapp.model.filter.FilterObject;

import java.util.ArrayList;
import java.util.List;

public class FilterLists {

    public static List<FilterObject> getGenres() {
        List<FilterObject> genres = new ArrayList<>();
        genres.add(new FilterObject("28", "Action"));
        genres.add(new FilterObject("12", "Adventure"));
        genres.add(new FilterObject("16", "Animation"));
        genres.add(new FilterObject("35", "Comedy"));
        genres.add(new FilterObject("80", "Crime"));
        genres.add(new FilterObject("99", "Documentary"));
        genres.add(new FilterObject("18", "Drama"));
        genres.add(new FilterObject("10751", "Family"));
        genres.add(new FilterObject("14", "Fantasy"));
        genres.add(new FilterObject("36", "History"));
        genres.add(new FilterObject("27", "Horror"));
        genres.add(new FilterObject("10402", "Music"));
        genres.add(new FilterObject("9648", "Mystery"));
        genres.add(new FilterObject("10749", "Romance"));
        genres.add(new FilterObject("878", "Science Fiction"));
        genres.add(new FilterObject("10770", "TV Movies"));
        genres.add(new FilterObject("53", "Thriller"));
        genres.add(new FilterObject("10752", "War"));
        genres.add(new FilterObject("37", "Western"));
        return genres;
    }

    public static List<FilterObject> getProductions() {
        List<FilterObject> productions = new ArrayList<>();
        productions.add(new FilterObject("3036", "Walt Disney Studios Motion Pictures"));
        productions.add(new FilterObject("17", "Warner Bros. Entertainment"));
        productions.add(new FilterObject("5", "Columbia Pictures"));
        productions.add(new FilterObject("25", "20th Century Fox"));
        productions.add(new FilterObject("21", "Metro-Goldwyn-Mayer"));
        productions.add(new FilterObject("33", "Universal Pictures"));
        productions.add(new FilterObject("4", "Paramount"));
        productions.add(new FilterObject("1632", "Lionsgate"));
        productions.add(new FilterObject("7", "DreamWorks"));
        productions.add(new FilterObject("19551", "Marvel Enterprises"));
        return productions;
    }

    public static List<FilterObject> getLanguages() {
        List<FilterObject> languages = new ArrayList<>();
        languages.add(new FilterObject("en", "English"));
        languages.add(new FilterObject("es", "Spanish"));
        languages.add(new FilterObject("fr", "French"));
        languages.add(new FilterObject("it", "Italian"));
        languages.add(new FilterObject("ru", "Russian"));
        languages.add(new FilterObject("sr", "Serbian"));
        languages.add(new FilterObject("el", "Greek"));
        languages.add(new FilterObject("hr", "Croatian"));
        languages.add(new FilterObject("de", "German"));
        return languages;
    }
}
