package com.singidunum.moviesinfoapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FiltersActivity extends AppCompatActivity {

    // TODO remember last selected

    private HashMap<String, String> genres = new HashMap<>();
    private HashMap<String, String> languages = new HashMap<>();
    private HashMap<String, String> productions = new HashMap<>();

    private final ArrayList<String> selectedGenres = new ArrayList<>();
    private final ArrayList<String> selectedLanguages = new ArrayList<>();
    private final ArrayList<String> selectedProductions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        putGenresInMap();
        putLanguagesInMap();
        putProductionsInMap();

        findViewById(R.id.save_filters).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFilters("genres", selectedGenres);
                saveFilters("languages", selectedLanguages);
                saveFilters("productions", selectedProductions);

                DatePicker from = findViewById(R.id.date_from);
                DatePicker to = findViewById(R.id.date_to);

                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("fromDate", from.getDayOfMonth() + "." + (from.getMonth() + 1) + "." + from.getYear() + ".").apply();
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("toDate", to.getDayOfMonth() + "." + (to.getMonth() + 1) + "." + to.getYear() + ".").apply();

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

    private void saveFilters(String filter, ArrayList<String> chosen) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
        Gson gson = new Gson();
        String genres, languages, productions;
        switch (filter) {
            case "genres":
                genres = gson.toJson(chosen);
                if (!checkIfEmpty(genres)) {
                    editor.putString("genres", genres).apply();
                }
                break;
            case "languages":
                languages = gson.toJson(chosen);
                if (!checkIfEmpty(languages)) {
                    editor.putString("languages", languages).apply();
                }
                break;
            default:
                productions = gson.toJson(chosen);
                if (!checkIfEmpty(productions)) {
                    editor.putString("productions", productions).apply();
                }
                break;
        }
    }

    private Boolean checkIfEmpty(String json) {
        return TextUtils.isEmpty(json);
    }

    private void createCheckboxes(LinearLayout layout, final ArrayList<String> selectedItems, HashMap<String, String> maps) {
        // Checkbox listener
        CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Map.Entry<String, String> tag = (Map.Entry<String, String>) buttonView.getTag();
                if (isChecked) {
                    selectedItems.add(tag.getValue());
                } else if (selectedItems.contains(tag.getValue())) {
                    selectedItems.remove(tag.getValue());
                }
            }
        };

        // Creating checkboxes
        for (Map.Entry<String, String> map : maps.entrySet()) {
            CheckBox checkBox = new CheckBox(getApplicationContext());
            checkBox.setPadding(0, 15, 0, 15);
            checkBox.setText(map.getValue());
            checkBox.setTag(map);
            layout.addView(checkBox);
            checkBox.setOnCheckedChangeListener(listener);
        }
    }

    private void putGenresInMap() {
        genres.put("28", "Action");
        genres.put("12", "Adventure");
        genres.put("16", "Animation");
        genres.put("35", "Comedy");
        genres.put("80", "Crime");
        genres.put("99", "Documentary");
        genres.put("18", "Drama");
        genres.put("10751", "Family");
        genres.put("14", "Fantasy");
        genres.put("36", "History");
        genres.put("27", "Horror");
        genres.put("10402", "Music");
        genres.put("9648", "Mystery");
        genres.put("10749", "Romance");
        genres.put("878", "Science Fiction");
        genres.put("10770", "TV Movie");
        genres.put("53", "Thriller");
        genres.put("10752", "War");
        genres.put("37", "Western");

        LinearLayout layout = findViewById(R.id.genres_layout);

        createCheckboxes(layout, selectedGenres, genres);
    }

    private void putLanguagesInMap() {
        languages.put("en", "English");
        languages.put("es", "Spanish");
        languages.put("fr", "French");
        languages.put("it", "Italian");
        languages.put("ru", "Russian");
        languages.put("sr", "Serbian");
        languages.put("el", "Greek");
        languages.put("hr", "Croatian");
        languages.put("de", "German");

        LinearLayout layout = findViewById(R.id.language_layout);

        createCheckboxes(layout, selectedLanguages, languages);
    }

    private void putProductionsInMap() {
        productions.put("3036", "Walt Disney Studios Motion Pictures");
        productions.put("17", "Warner Bros. Entertainment");
        productions.put("5", "Columbia Pictures");
        productions.put("25", "20th Century Fox");
        productions.put("21", "Metro-Goldwyn-Mayer");
        productions.put("33", "Universal Pictures");
        productions.put("4", "Paramount");
        productions.put("1632", "Lionsgate");
        productions.put("7", "DreamWorks");
        productions.put("19551", "Marvel Enterprises");

        LinearLayout layout = findViewById(R.id.production_layout);

        createCheckboxes(layout, selectedProductions, productions);
    }
}
