package com.singidunum.moviesinfoapp.api;

import com.singidunum.moviesinfoapp.model.api.pictures.MoviePicturesResult;
import com.singidunum.moviesinfoapp.model.api.credits.MovieCreditsResult;
import com.singidunum.moviesinfoapp.model.api.movie.MovieResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoviesApi {

    // https://api.themoviedb.org/3/discover/movie?
    // api_key=f7c765a60e4d8a73f2e3686371956f8e&
    // language=en-US&
    // sort_by=popularity.desc&
    // page=1&
    // primary_release_date.gte=2010-09-15&
    // primary_release_date.lte=2014-10-22&
    // with_companies=5&
    // with_genres=28&
    // with_original_language=en

    @GET("discover/movie/")
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

    // https://api.themoviedb.org/3/movie/
    // 383498/credits?
    // api_key=f7c765a60e4d8a73f2e3686371956f8e

    @GET("movie/{movie_id}/credits")
    Call<MovieCreditsResult> getCredits(
            @Path("movie_id") int movieId,
            @Query("api_key") String apiKey
    );

    // https://api.themoviedb.org/3/movie/
    // 383498/images?
    // api_key=f7c765a60e4d8a73f2e3686371956f8e

    @GET("movie/{movie_id}/images")
    Call<MoviePicturesResult> getPictures(
            @Path("movie_id") int movieId,
            @Query("api_key") String apiKey
    );

    // TODO implement search box
    // https://api.themoviedb.org/3/search/movie?
    // api_key=f7c765a60e4d8a73f2e3686371956f8e&
    // language=en-US&
    // query=deadpool&
    // page=1&
    // include_adult=false

    /*@GET("search/movie")
    Call<MovieSearchResult> findMovie(
            @Query("")
    )*/
}
