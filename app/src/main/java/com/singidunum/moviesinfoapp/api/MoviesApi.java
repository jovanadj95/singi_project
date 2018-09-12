package com.singidunum.moviesinfoapp.api;

import com.singidunum.moviesinfoapp.model.api.actors.Actor;
import com.singidunum.moviesinfoapp.model.api.actors.ActorFilmography;
import com.singidunum.moviesinfoapp.model.api.credits.MovieCreditsResult;
import com.singidunum.moviesinfoapp.model.api.movie.Movie;
import com.singidunum.moviesinfoapp.model.api.movies.MovieResult;
import com.singidunum.moviesinfoapp.model.api.pictures.MoviePicturesResult;

import java.util.ArrayList;

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
            @Query("include_adult") Boolean includeAdult,
            @Query("page") int page,
            @Query("primary_release_date.gte") String primaryReleaseDateGte,
            @Query("primary_release_date.lte") String primaryReleaseDateLte,
            @Query("with_companies") ArrayList<String> withCompanies,
            @Query("with_genres") ArrayList<String> withGenres,
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

    // https://api.themoviedb.org/3/person/
    // 10859?
    // api_key=f7c765a60e4d8a73f2e3686371956f8e

    @GET("person/{actor_Id}")
    Call<Actor> getActor(
            @Path("actor_Id") int id,
            @Query("api_key") String apiKey
    );

    // https://api.themoviedb.org/3/person/
    // 10859/movie_credits?
    // api_key=f7c765a60e4d8a73f2e3686371956f8e
    // &language=en-US
    @GET("person/{actorId}/movie_credits")
    Call<ActorFilmography> getActorFilmography(
            @Path("actorId") int id,
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    // https://api.themoviedb.org/3/movie/
    // 383498?
    // api_key=f7c765a60e4d8a73f2e3686371956f8e&
    // language=en-US

    @GET("movie/{movie_id}")
    Call<Movie> getMovie(
            @Path("movie_id") int movieId,
            @Query("api_key") String apiKey,
            @Query("language") String language
    );
}
