package com.singidunum.moviesinfoapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.singidunum.moviesinfoapp.BuildConfig;
import com.singidunum.moviesinfoapp.adapter.MoviesAdapter;
import com.singidunum.moviesinfoapp.R;
import com.singidunum.moviesinfoapp.api.MoviesApi;
import com.singidunum.moviesinfoapp.model.api.Movie;
import com.singidunum.moviesinfoapp.model.api.MovieResult;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    // TODO call to API TMDB
    // TODO create adapter for previewing movies (use movie layout for each movie)
    // TODO add click listener for each movie that will open movie details activity
    // TODO read last filters and preview the filtered movies
    // TODO include fragments and landscape orientation

    private List<Movie> moviesList;
    private RecyclerView recyclerView;

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

        moviesList = new ArrayList<>();
        recyclerView = findViewById(R.id.movies_list);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        createRetrofitCall();
    }

    private void createRetrofitCall() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MoviesApi moviesApi = retrofit.create(MoviesApi.class);

        // TODO implement filters if they are picked
        // TODO try to implement append_to_response for actors
        Call<MovieResult> call = moviesApi.getMovies(BuildConfig.API_KEY, "en-US",
                "popularity.desc", 1, "2010-09-15",
                "2018-10-22", "5", "28", "en");

        getMovies(call);
    }

    // TODO get next page
    private void getMovies(Call<MovieResult> call) {
        call.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                if (response.body() != null) {
                    MovieResult result = response.body();
                    if (result != null) {
                        moviesList = result.getMovies();
                        recyclerView.setAdapter(new MoviesAdapter(getApplicationContext(), moviesList));
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
