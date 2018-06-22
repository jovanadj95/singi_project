package com.singidunum.moviesinfoapp.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedStorageData {

    private static final String GENRES = "GENRES";
    private static final String DATE_FROM = "DATE_FROM";
    private static final String DATE_TO = "DATE_TO";
    private static final String LANGUAGES = "LANGUAGES";
    private static final String PRODUCTION_HOUSES = "PRODUCTION_HOUSES";

    private static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setGenres(Context context, String genres) {
        getSharedPreferences(context).edit().putString(GENRES, genres).apply();
    }

    public static String getGenres(Context context) {
        return getSharedPreferences(context).getString(GENRES, null);
    }

    public static void setDateFrom(Context context, String dateFrom) {
        getSharedPreferences(context).edit().putString(DATE_FROM, dateFrom).apply();
    }

    public static String getDateFrom(Context context) {
        return getSharedPreferences(context).getString(DATE_FROM, null);
    }

    public static void setDateTo(Context context, String dateTo) {
        getSharedPreferences(context).edit().putString(DATE_TO, dateTo).apply();
    }

    public static String getDateTo(Context context) {
        return getSharedPreferences(context).getString(DATE_TO, null);
    }

    public static void setLanguages(Context context, String languages) {
        getSharedPreferences(context).edit().putString(LANGUAGES, languages).apply();
    }

    public static String getLanguages(Context context) {
        return getSharedPreferences(context).getString(LANGUAGES, null);
    }

    public static void setProductionHouses(Context context, String productionHouses) {
        getSharedPreferences(context).edit().putString(PRODUCTION_HOUSES, productionHouses).apply();
    }

    public static String getProductionHouses(Context context) {
        return getSharedPreferences(context).getString(PRODUCTION_HOUSES, null);
    }
}
