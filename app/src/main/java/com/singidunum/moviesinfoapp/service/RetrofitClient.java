package com.singidunum.moviesinfoapp.service;

import com.singidunum.moviesinfoapp.BuildConfig;
import com.singidunum.moviesinfoapp.api.MoviesApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;

    public static MoviesApi getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(MoviesApi.class);
    }
}
