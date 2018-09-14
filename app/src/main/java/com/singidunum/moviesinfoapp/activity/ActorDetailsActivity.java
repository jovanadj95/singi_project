package com.singidunum.moviesinfoapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.singidunum.moviesinfoapp.BuildConfig;
import com.singidunum.moviesinfoapp.R;
import com.singidunum.moviesinfoapp.adapter.FilmographyAdapter;
import com.singidunum.moviesinfoapp.model.api.actors.Actor;
import com.singidunum.moviesinfoapp.model.api.actors.ActorFilmography;
import com.singidunum.moviesinfoapp.model.api.actors.Cast;
import com.singidunum.moviesinfoapp.service.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActorDetailsActivity extends AppCompatActivity {

    private RecyclerView rvFilmography;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actor_details);

        int actorId = getIntent().getIntExtra("actorId", 0);
        getActors(actorId, BuildConfig.API_KEY);

        rvFilmography = findViewById(R.id.details_actor_filmography);
        rvFilmography.setLayoutManager(new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false));
        getFilmography(actorId, BuildConfig.API_KEY);
    }

    private void getActors(int actorId, String apiKey) {
        RetrofitClient.getClient().getActor(actorId, apiKey).enqueue(new Callback<Actor>() {
            @Override
            public void onResponse(Call<Actor> call, Response<Actor> response) {
                if (response.body() != null) {
                    Actor actor = response.body();
                    if (actor != null) {
                        Picasso.get()
                                .load(BuildConfig.API_IMG_BASE + "h632" + actor.getProfilePath())
                                .into((ImageView) findViewById(R.id.details_actor_picture));

                        ((TextView) findViewById(R.id.details_actor_name)).setText(actor.getName());
                        ((TextView) findViewById(R.id.details_actor_place_of_birth)).setText(new StringBuilder().append("Place of birth: ").append(actor.getPlaceOfBirth()).toString());
                        ((TextView) findViewById(R.id.details_actor_gender)).setText(new StringBuilder().append("Gender: ").append(actor.getGender() == 1 ? "Female" : "Male").toString());
                        ((TextView) findViewById(R.id.details_actor_biography)).setText(actor.getBiography());

                        String date = actor.getBirthday();
                        ((TextView) findViewById(R.id.details_actor_birthday)).setText(new StringBuilder()
                                .append("Birthday: ")
                                .append(date.substring(date.lastIndexOf("-") + 1))
                                .append(".")
                                .append(date.substring(date.indexOf("-") + 1, date.lastIndexOf("-")))
                                .append(".")
                                .append(date.substring(0, date.indexOf("-")))
                                .append(".").toString());

                        if (actor.getDeathday() != null) {
                            TextView vDeathday = findViewById(R.id.details_actor_death);
                            vDeathday.setVisibility(View.VISIBLE);
                            String deathday = actor.getDeathday().toString();
                            vDeathday.setText(new StringBuilder()
                                    .append("Died on: ")
                                    .append(deathday.substring(deathday.lastIndexOf("-") + 1))
                                    .append(".")
                                    .append(deathday.substring(deathday.indexOf("-") + 1, deathday.lastIndexOf("-")))
                                    .append(".")
                                    .append(deathday.substring(0, deathday.indexOf("-")))
                                    .append(".").toString());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Actor> call, Throwable t) {
            }
        });
    }

    private void getFilmography(int actorId, String apiKey) {
        RetrofitClient.getClient().getActorFilmography(actorId, apiKey, "en-US").enqueue(new Callback<ActorFilmography>() {
            @Override
            public void onResponse(Call<ActorFilmography> call, Response<ActorFilmography> response) {
                if (response.body() != null) {
                    ActorFilmography result = response.body();
                    if (result != null) {
                        List<Cast> cast = result.getCast();
                        Collections.sort(cast, new Comparator<Cast>() {
                            @Override
                            public int compare(Cast o1, Cast o2) {
                                if (o1.getReleaseDate() == null && o2.getReleaseDate() == null) {
                                    return 0;
                                } else if (o1.getReleaseDate() == null) {
                                    return 1;
                                } else if (o2.getReleaseDate() == null) {
                                    return -1;
                                }

                                if (o1.getReleaseDate().equals("") && o2.getReleaseDate().equals("")) {
                                    return 0;
                                } else if (o1.getReleaseDate().equals("")) {
                                    return 1;
                                } else if (o2.getReleaseDate().equals("")) {
                                    return -1;
                                }
                                return o1.getReleaseDate().compareTo(o2.getReleaseDate());
                            }
                        });
                        Collections.reverse(cast);
                        rvFilmography.setAdapter(new FilmographyAdapter(ActorDetailsActivity.this, cast));
                    }
                }
            }

            @Override
            public void onFailure(Call<ActorFilmography> call, Throwable t) {
            }
        });
    }
}
