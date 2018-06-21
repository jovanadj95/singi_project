package com.singidunum.moviesinfoapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.singidunum.moviesinfoapp.BuildConfig;
import com.singidunum.moviesinfoapp.adapter.MoviesAdapter;
import com.singidunum.moviesinfoapp.R;
import com.singidunum.moviesinfoapp.api.MoviesApi;
import com.singidunum.moviesinfoapp.model.api.movie.Movie;
import com.singidunum.moviesinfoapp.model.api.movie.MovieResult;
import com.singidunum.moviesinfoapp.service.ApiRetrofit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    // TODO read last filters and preview the filtered movies
    // TODO include fragments and landscape orientation

    private List<Movie> moviesList;
    private RecyclerView rvMovieList;

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
        rvMovieList = findViewById(R.id.movies_list);
        rvMovieList.setLayoutManager(new LinearLayoutManager(this));
        createRetrofitGetMoviesCall();
    }

    private void createRetrofitGetMoviesCall() {
        ApiRetrofit apiRetrofit = new ApiRetrofit();
        MoviesApi moviesApi = apiRetrofit.getApiRetrofit();

        // TODO implement filters if they are picked
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
                        rvMovieList.setAdapter(new MoviesAdapter(MainActivity.this, moviesList));
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {
            }
        });
    }
}
