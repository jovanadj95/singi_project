package com.singidunum.moviesinfoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    // TODO call to API TMDB
    // TODO create adapter for previewing movies (use movie layout for each movie)
    // TODO add click listener for each movie that will open movie details activity
    // TODO read last filters and preview the filtered movies
    // TODO go to change filters activity
    // TODO include fragments and landscape orientation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startActivity(new Intent(this, FiltersActivity.class));
    }
}
