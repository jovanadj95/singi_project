package com.singidunum.moviesinfoapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.singidunum.moviesinfoapp.BuildConfig;
import com.singidunum.moviesinfoapp.R;
import com.singidunum.moviesinfoapp.adapter.MoviesAdapter;
import com.singidunum.moviesinfoapp.model.api.movies.Movies;
import com.singidunum.moviesinfoapp.model.api.movies.MovieResult;
import com.singidunum.moviesinfoapp.model.filter.FilterObject;
import com.singidunum.moviesinfoapp.service.RetrofitClient;
import com.singidunum.moviesinfoapp.service.SharedStorageData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.NavigationListener {

    private List<Movies> moviesList;
    private MoviesAdapter adapter;
    private int page;

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
        RecyclerView rvMovieList = findViewById(R.id.movies_list);
        rvMovieList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MoviesAdapter(MainActivity.this, moviesList);
        rvMovieList.setAdapter(adapter);
        createRetrofitGetMoviesCall(1);
    }

    private void createRetrofitGetMoviesCall(int page) {
        Call<MovieResult> call = RetrofitClient.getClient().getMovies(BuildConfig.API_KEY, "en-US",
                "popularity.desc", false, page, SharedStorageData.getDateFrom(this),
                SharedStorageData.getDateTo(this), getId("Production houses"), getId("Genres"), getLanguage());
        getMovies(call);
    }

    private String getLanguage() {
        FilterObject language = new Gson().fromJson(SharedStorageData.getLanguages(this), FilterObject.class);
        return language == null ? "en" : language.getId();
    }

    private ArrayList<String> getId(String filter) {
        String data = null;
        switch (filter) {
            case "Genres":
                data = SharedStorageData.getGenres(this);
                break;
            case "Production houses":
                data = SharedStorageData.getProductionHouses(this);
                break;
        }
        if (data != null && !data.equals("[]")) {
            List<FilterObject> list = new Gson().fromJson(data, new TypeToken<ArrayList<FilterObject>>() {
            }.getType());

            ArrayList<String> ids = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                ids.add(list.get(i).getId());
            }
            return ids;
        }
        return null;
    }

    private void getMovies(Call<MovieResult> call) {
        call.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                if (response.body() != null) {
                    MovieResult result = response.body();
                    if (result != null) {
                        moviesList = result.getMovies();
                        page = page == 0 ? 1 : page;
                        adapter.updateData(result.getMovies(), page == result.getTotalPages() ? 0 : 1);
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {
            }
        });
    }

    @Override
    public void onPagination(int page) {
        this.page = page;
        createRetrofitGetMoviesCall(page);
    }
}
