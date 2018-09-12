package com.singidunum.moviesinfoapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.singidunum.moviesinfoapp.BuildConfig;
import com.singidunum.moviesinfoapp.R;
import com.singidunum.moviesinfoapp.activity.MovieDetailsActivity;
import com.singidunum.moviesinfoapp.model.api.actors.Cast;
import com.singidunum.moviesinfoapp.model.api.movie.Movie;
import com.singidunum.moviesinfoapp.service.ApiRetrofit;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilmographyAdapter extends RecyclerView.Adapter<FilmographyAdapter.CastViewHolder> {

    private Context context;
    private List<Cast> castList;
    public static final String TAG = "FilmographyAdapter";

    public FilmographyAdapter(Context context, List<Cast> castList) {
        this.context = context;
        this.castList = castList;
    }

    @NonNull
    @Override
    public CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CastViewHolder(LayoutInflater.from(context).inflate(R.layout.filmography_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CastViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        final Cast cast = castList.get(position);
        if (cast.getCharacter() != null && !cast.getCharacter().isEmpty() && cast.getPosterPath() != null && cast.getReleaseDate() != null && !cast.getReleaseDate().isEmpty()) {
            holder.character.setText("Character: " + cast.getCharacter());
            if (cast.getTitle() != null) {
                holder.title.setText("Title: " + cast.getTitle());
            }
            holder.year.setText("Year: " + cast.getReleaseDate().substring(0, cast.getReleaseDate().indexOf("-")));
            Picasso.get()
                    .load(BuildConfig.API_IMG_BASE + "w185" + cast.getPosterPath())
                    .into(holder.image);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getMovieDetails(cast.getId());
                }
            });
        }
    }

    private void getMovieDetails(Integer id) {
        if (id != null) {
            ApiRetrofit.getApiRetrofit().getMovie(id, BuildConfig.API_KEY, "en-US").enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(Call<Movie> call, Response<Movie> response) {
                    Intent intent = new Intent(context, MovieDetailsActivity.class);
                    intent.putExtra("source", TAG);
                    intent.putExtra("movie", new Gson().toJson(response.body()));
                    context.startActivity(intent);
                }

                @Override
                public void onFailure(Call<Movie> call, Throwable t) {
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return castList.size();
    }

    static class CastViewHolder extends RecyclerView.ViewHolder {
        private TextView character;
        private TextView title;
        private TextView year;
        private ImageView image;

        CastViewHolder(View itemView) {
            super(itemView);
            character = itemView.findViewById(R.id.filmography_character);
            title = itemView.findViewById(R.id.filmography_title);
            year = itemView.findViewById(R.id.filmography_year);
            image = itemView.findViewById(R.id.filmography_poster);
        }
    }
}
