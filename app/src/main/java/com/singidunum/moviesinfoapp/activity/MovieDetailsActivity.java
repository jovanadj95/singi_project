package com.singidunum.moviesinfoapp.activity;

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
import com.singidunum.moviesinfoapp.adapter.FilmographyAdapter;
import com.singidunum.moviesinfoapp.adapter.MoviesAdapter;
import com.singidunum.moviesinfoapp.adapter.PictureAdapter;
import com.singidunum.moviesinfoapp.api.MoviesApi;
import com.singidunum.moviesinfoapp.model.api.credits.MovieCreditsResult;
import com.singidunum.moviesinfoapp.model.api.movie.Movie;
import com.singidunum.moviesinfoapp.model.api.movies.Movies;
import com.singidunum.moviesinfoapp.model.api.pictures.MoviePicturesResult;
import com.singidunum.moviesinfoapp.service.ApiRetrofit;
import com.singidunum.moviesinfoapp.service.FilterLists;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsActivity extends AppCompatActivity {

    private RecyclerView rvCast;
    private RecyclerView rvPictures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        int movieId = 0;

        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null && bundle.containsKey("source")) {
                if (MoviesAdapter.TAG.equals(bundle.getString("source"))) {
                    String movieJson = getIntent().getStringExtra("movie");
                    Movies movie = new Gson().fromJson(movieJson, Movies.class);

                    movieId = movie.getId();

                    StringBuilder genreBuilder = new StringBuilder();
                    for (int i = 0; i < movie.getGenreIds().size(); i++) {
                        for (int j = 0; j < FilterLists.getGenres().size(); j++) {
                            if (Integer.valueOf(FilterLists.getGenres().get(j).getId()).equals(movie.getGenreIds().get(i))) {
                                if (genreBuilder.length() >= 1) {
                                    genreBuilder.append(", ");
                                }
                                genreBuilder.append(FilterLists.getGenres().get(j).getDisplayName());
                            }
                        }
                    }

                    setDetails(movie.getTitle(),
                            String.valueOf(movie.getVoteAverage()),
                            movie.getOverview(),
                            movie.getReleaseDate(),
                            genreBuilder.toString());
                } else if (FilmographyAdapter.TAG.equals(bundle.getString("source"))) {
                    String movieJson = getIntent().getStringExtra("movie");
                    Movie movie = new Gson().fromJson(movieJson, Movie.class);

                    movieId = movie.getId();

                    StringBuilder genreBuilder = new StringBuilder();
                    for (int i = 0; i < movie.getGenres().size(); i++) {
                        if (genreBuilder.length() >= 1) {
                            genreBuilder.append(", ");
                        }
                        genreBuilder.append(movie.getGenres().get(i).getName());
                    }
                    setDetails(movie.getTitle(),
                            String.valueOf(movie.getVoteAverage()),
                            movie.getOverview(),
                            movie.getReleaseDate(),
                            genreBuilder.toString());
                }

                rvCast = findViewById(R.id.actors_list);
                rvCast.setLayoutManager(new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false));
                getCreditsCall(ApiRetrofit.getApiRetrofit(), movieId);

                rvPictures = findViewById(R.id.pictures_list);
                rvPictures.setLayoutManager(new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false));
                getPicturesCall(ApiRetrofit.getApiRetrofit(), movieId);
            }
        }
    }

    private void setDetails(String title, String voteAverage, String overview, String date, String genres) {
        ((TextView) findViewById(R.id.movie_title_details)).setText(title);
        ((TextView) findViewById(R.id.imdb_vote_average)).setText(voteAverage);
        ((TextView) findViewById(R.id.movie_plot)).setText(overview);
        ((TextView) findViewById(R.id.movie_release_details)).setText(new StringBuilder()
                .append(date.substring(date.lastIndexOf("-") + 1))
                .append(".")
                .append(date.substring(date.indexOf("-") + 1, date.lastIndexOf("-")))
                .append(".")
                .append(date.substring(0, date.indexOf("-")))
                .append(".").toString());
        ((TextView) findViewById(R.id.movie_genres_details)).setText(genres);
    }

    private void getPicturesCall(MoviesApi moviesApi, Integer id) {
        moviesApi.getPictures(id, BuildConfig.API_KEY).enqueue(new Callback<MoviePicturesResult>() {
            @Override
            public void onResponse(Call<MoviePicturesResult> call, Response<MoviePicturesResult> response) {
                if (response.body() != null) {
                    MoviePicturesResult result = response.body();
                    if (result != null) {
                        rvPictures.setAdapter(new PictureAdapter(MovieDetailsActivity.this, result.getBackdrops()));
                    }
                }
            }

            @Override
            public void onFailure(Call<MoviePicturesResult> call, Throwable t) {
            }
        });
    }

    private void getCreditsCall(MoviesApi moviesApi, Integer id) {
        moviesApi.getCredits(id, BuildConfig.API_KEY).enqueue(new Callback<MovieCreditsResult>() {
            @Override
            public void onResponse(Call<MovieCreditsResult> call, Response<MovieCreditsResult> response) {
                if (response.body() != null) {
                    MovieCreditsResult result = response.body();
                    if (result != null) {
                        rvCast.setAdapter(new CastAdapter(MovieDetailsActivity.this, result.getCast()));
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieCreditsResult> call, Throwable t) {
            }
        });
    }
}
