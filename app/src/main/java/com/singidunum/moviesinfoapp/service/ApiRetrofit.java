package com.singidunum.moviesinfoapp.service;

import com.singidunum.moviesinfoapp.BuildConfig;
import com.singidunum.moviesinfoapp.api.MoviesApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRetrofit {

    public static MoviesApi getApiRetrofit() {
        Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(MoviesApi.class);
    }
}
