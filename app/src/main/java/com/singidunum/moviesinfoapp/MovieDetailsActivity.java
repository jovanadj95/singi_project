package com.singidunum.moviesinfoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MovieDetailsActivity extends AppCompatActivity {

    // TODO add go back button or use backstack (probably better option)
    // TODO add for corresponding movie all the available actors through api and show their pictures
    // TODO connect IMDB rating to app rating
    // TODO implement movie pictures

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
    }
}