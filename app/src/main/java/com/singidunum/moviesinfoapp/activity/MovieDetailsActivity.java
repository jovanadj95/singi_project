package com.singidunum.moviesinfoapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.singidunum.moviesinfoapp.BuildConfig;
import com.singidunum.moviesinfoapp.R;
import com.singidunum.moviesinfoapp.adapter.CastAdapter;
import com.singidunum.moviesinfoapp.adapter.PictureAdapter;
import com.singidunum.moviesinfoapp.api.MoviesApi;
import com.singidunum.moviesinfoapp.model.api.credits.Cast;
import com.singidunum.moviesinfoapp.model.api.credits.MovieCreditsResult;
import com.singidunum.moviesinfoapp.model.api.movie.Movie;
import com.singidunum.moviesinfoapp.model.api.pictures.Backdrop;
import com.singidunum.moviesinfoapp.model.api.pictures.MoviePicturesResult;
import com.singidunum.moviesinfoapp.service.ApiRetrofit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsActivity extends AppCompatActivity {

    private List<Cast> cast = new ArrayList<>();
    private List<Backdrop> pictures = new ArrayList<>();
    private RecyclerView rvCast;
    private RecyclerView rvPictures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Intent intent = getIntent();
        String movieJson = intent.getStringExtra("movie");
        Movie movie = new Gson().fromJson(movieJson, Movie.class);

        ((TextView) findViewById(R.id.movie_title_details)).setText(movie.getTitle());
        ((TextView) findViewById(R.id.imdb_vote_average)).setText(String.valueOf(movie.getVoteAverage()));
        ((TextView) findViewById(R.id.movie_plot)).setText(movie.getOverview());

        MoviesApi moviesApi = createRetrofitApi();

        rvCast = findViewById(R.id.actors_list);
        rvCast.setLayoutManager(new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false));
        getCreditsCall(moviesApi, movie.getId());

        rvPictures = findViewById(R.id.pictures_list);
        rvPictures.setLayoutManager(new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false));
        getPicturesCall(moviesApi, movie.getId());
    }

    private MoviesApi createRetrofitApi() {
        ApiRetrofit apiRetrofit = new ApiRetrofit();
        return apiRetrofit.getApiRetrofit();
    }

    private void getPicturesCall(MoviesApi moviesApi, Integer id) {
        Call<MoviePicturesResult> call = moviesApi.getPictures(id, BuildConfig.API_KEY);
        call.enqueue(new Callback<MoviePicturesResult>() {
            @Override
            public void onResponse(Call<MoviePicturesResult> call, Response<MoviePicturesResult> response) {
                if (response.body() != null) {
                    MoviePicturesResult result = response.body();
                    if (result != null) {
                        pictures = result.getBackdrops();
                        rvPictures.setAdapter(new PictureAdapter(MovieDetailsActivity.this, pictures));
                    }
                }
            }

            @Override
            public void onFailure(Call<MoviePicturesResult> call, Throwable t) {
            }
        });
    }

    private void getCreditsCall(MoviesApi moviesApi, Integer id) {
        Call<MovieCreditsResult> call = moviesApi.getCredits(id, BuildConfig.API_KEY);
        call.enqueue(new Callback<MovieCreditsResult>() {
            @Override
            public void onResponse(Call<MovieCreditsResult> call, Response<MovieCreditsResult> response) {
                if (response.body() != null) {
                    MovieCreditsResult result = response.body();
                    if (result != null) {
                        cast = result.getCast();
                        rvCast.setAdapter(new CastAdapter(MovieDetailsActivity.this, cast));
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieCreditsResult> call, Throwable t) {
            }
        });
    }
}
