package com.singidunum.moviesinfoapp;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // TODO call to API TMDB
    // TODO create adapter for previewing movies (use movie layout for each movie)
    // TODO add click listener for each movie that will open movie details activity
    // TODO read last filters and preview the filtered movies
    // TODO include fragments and landscape orientation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.change_filters).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), FiltersActivity.class));
            }
        });

        /*ArrayList<String> selectedGenres = getFilters("genres");
        ArrayList<String> selectedLanguages = getFilters("languages");
        ArrayList<String> selectedProductions = getFilters("productions");

        String from = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("from", "");
        String to = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("to", "");

        if (selectedGenres != null) {
            for (int i = 0; i < selectedGenres.size(); i++) {
                Log.d("FICOFICO", "selectedGenres: " + selectedGenres.get(i));
            }
        }
        if (!from.equals("") && !to.equals("")) {
            Log.d("FICOFICO", "from: " + from);
            Log.d("FICOFICO", "to: " + to);
        }*/
    }

    private ArrayList<String> getFilters(String key) {
        Gson gson = new Gson();
        String json = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(key, "");
        return gson.fromJson(json, new TypeToken<ArrayList<String>>() {
        }.getType());
    }
}
