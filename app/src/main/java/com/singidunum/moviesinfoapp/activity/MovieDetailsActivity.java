package com.singidunum.moviesinfoapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.singidunum.moviesinfoapp.R;

public class MovieDetailsActivity extends AppCompatActivity {

    // TODO add go back button
    // TODO add for corresponding movie all the available actors through api and show their pictures
    // TODO connect IMDB rating to app rating
    // TODO implement movie pictures

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
    }
}
