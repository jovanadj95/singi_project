package com.singidunum.moviesinfoapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.singidunum.moviesinfoapp.BuildConfig;
import com.singidunum.moviesinfoapp.R;
import com.singidunum.moviesinfoapp.model.api.Actor;
import com.singidunum.moviesinfoapp.service.ApiRetrofit;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActorDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actor_details);

        ApiRetrofit.getApiRetrofit().getActor(getIntent().getIntExtra("actorId", 0), BuildConfig.API_KEY).enqueue(new Callback<Actor>() {
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
                    }
                }
            }

            @Override
            public void onFailure(Call<Actor> call, Throwable t) {
            }
        });
    }
}
