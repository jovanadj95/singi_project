package com.singidunum.moviesinfoapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FiltersActivity extends AppCompatActivity {

    private HashMap<String, String> genres = new HashMap<>();
    private HashMap<String, String> languages = new HashMap<>();
    private HashMap<String, String> productions = new HashMap<>();

    private ArrayList<String> selectedGenres = new ArrayList<>();
    private ArrayList<String> selectedLanguages = new ArrayList<>();
    private ArrayList<String> selectedProductions = new ArrayList<>();

    private ArrayList<String> selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        findViewById(R.id.save_filters).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFilters("genres", selectedGenres);
                saveFilters("languages", selectedLanguages);
                saveFilters("productions", selectedProductions);

                saveDate("from");
                saveDate("to");

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        createFilterGroups();
    }

    private void createFilterGroups() {
        final LinearLayout filterGroups = findViewById(R.id.filter_groups);
        String[] filters = {"Genres", "Date from", "Date to", "Languages", "Production"};

        for (final String filter : filters) {
            final View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.arrow_right, filterGroups, false);
            filterGroups.addView(view);
            ((TextView) view.findViewById(R.id.filter_group_name_right)).setText(filter + ":");

            view.findViewById(R.id.button_right).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showFilters(view, filter, filterGroups);
                }
            });
        }
    }

    private void showFilters(final View view, String filter, LinearLayout filterGroups) {
        final View newView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.arrow_down, filterGroups, false);
        int index = filterGroups.indexOfChild(view);
        filterGroups.addView(newView, index + 1);
        ((TextView) newView.findViewById(R.id.filter_group_name_down)).setText(filter + ":");
        view.setVisibility(View.GONE);

        switch (filter) {
            case "Genres":
                putGenresInMap(newView);
                break;
            case "Languages":
                putLanguagesInMap(newView);
                break;
            case "Date from":
                putDatePicker(newView, filter);
                break;
            case "Date to":
                putDatePicker(newView, filter);
                break;
            case "Production":
                putProductionsInMap(newView);
                break;
        }

        newView.findViewById(R.id.button_down).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView(view, newView);
            }
        });
    }

    private void putDatePicker(View view, String filter) {
        LinearLayout layout = view.findViewById(R.id.layout);
        View newView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.date_picker, layout, false);

        layout.addView(newView);
        DatePicker datePicker = newView.findViewById(R.id.date_picker);
        if (filter.equals("Date from")) {
            datePicker.setTag("from");
        } else {
            datePicker.setTag("to");
        }
        setSavedDate(datePicker);
    }

    private void setSavedDate(DatePicker datePicker) {
        String date = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(datePicker.getTag().toString(), "");
        Log.d("FICOFICO", "setSavedDate: " + date);
        if (!TextUtils.isEmpty(date)) {
            int year = Integer.parseInt(date.substring(date.indexOf(".", date.indexOf(".") + 1) + 1, date.lastIndexOf(".")));
            int month = Integer.parseInt(date.substring(date.indexOf(".") + 1, date.indexOf(".", date.indexOf(".") + 1)));
            int day = Integer.parseInt(date.substring(0, date.indexOf(".")));
            datePicker.updateDate(year, month - 1, day);
        }
    }

    private void saveDate(String key) {
        View view = findViewById(R.id.date_picker_layout);
        if (view.findViewWithTag("from") != null && key.equals("to")) {
            ViewGroup group = (ViewGroup) view.getParent();
            group.removeView(view);
            view = findViewById(R.id.date_picker_layout);
        }
        DatePicker datePicker = view.findViewWithTag(key);
        if (datePicker != null) {
            String date = datePicker.getDayOfMonth() + "." + (datePicker.getMonth() + 1) + "." + datePicker.getYear() + ".";
            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString(key, date).apply();
        }
    }

    private void removeView(View view, View newView) {
        view.setVisibility(View.VISIBLE);
        newView.setVisibility(View.GONE);
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

    private ArrayList<String> createCheckboxes(LinearLayout layout, final ArrayList<String> selectedItems, HashMap<String, String> maps) {
        if (selectedItems == null) {
            selected = new ArrayList<>();
        } else {
            selected = selectedItems;
        }

        // Checkbox listener
        CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Map.Entry<String, String> tag = (Map.Entry<String, String>) buttonView.getTag();
                if (isChecked) {
                    selected.add(tag.getValue());
                } else if (selected.contains(tag.getValue())) {
                    selected.remove(tag.getValue());
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
            if (selected != null && selected.contains(map.getValue())) {
                checkBox.setChecked(true);
            }
            checkBox.setOnCheckedChangeListener(listener);
        }
        return selected;
    }

    private void putGenresInMap(View view) {
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

        LinearLayout layout = view.findViewById(R.id.layout);
        selectedGenres = createCheckboxes(layout, getFilters("genres"), genres);
    }

    private void putLanguagesInMap(View view) {
        languages.put("en", "English");
        languages.put("es", "Spanish");
        languages.put("fr", "French");
        languages.put("it", "Italian");
        languages.put("ru", "Russian");
        languages.put("sr", "Serbian");
        languages.put("el", "Greek");
        languages.put("hr", "Croatian");
        languages.put("de", "German");

        LinearLayout layout = view.findViewById(R.id.layout);
        selectedLanguages = createCheckboxes(layout, getFilters("languages"), languages);
    }

    private void putProductionsInMap(View view) {
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

        LinearLayout layout = view.findViewById(R.id.layout);
        selectedProductions = createCheckboxes(layout, getFilters("productions"), productions);
    }

    private ArrayList<String> getFilters(String key) {
        Gson gson = new Gson();
        String json = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(key, "");
        return gson.fromJson(json, new TypeToken<ArrayList<String>>() {
        }.getType());
    }
}
