package com.singidunum.moviesinfoapp.api;

import com.singidunum.moviesinfoapp.model.api.MovieResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesApi {
    // TODO remember backdrop path for movie details activity

    // https://api.themoviedb.org/3/discover/movie?
    // api_key=f7c765a60e4d8a73f2e3686371956f8e&
    // language=en-US&
    // sort_by=popularity.desc&
    // page=1&
    // primary_release_date.gte=2010-09-15&
    // primary_release_date.lte=2014-10-22&
    // with_companies=5&
    // with_genres=28&
    // with_original_language=en&

    @GET("/3/discover/movie/")
    Call<MovieResult> getMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("sort_by") String sortBy,
            @Query("page") int page,
            @Query("primary_release_date.gte") String primaryReleaseDateGte,
            @Query("primary_release_date.lte") String primaryReleaseDateLte,
            @Query("with_companies") String withCompanies,
            @Query("with_genres") String withGenres,
            @Query("with_original_language") String withOriginalLanguage
    );
}
