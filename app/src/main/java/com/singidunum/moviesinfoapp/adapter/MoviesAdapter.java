package com.singidunum.moviesinfoapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.singidunum.moviesinfoapp.BuildConfig;
import com.singidunum.moviesinfoapp.R;
import com.singidunum.moviesinfoapp.activity.MovieDetailsActivity;
import com.singidunum.moviesinfoapp.model.api.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private Context context;
    private List<Movie> moviesList;

    public MoviesAdapter(Context context, List<Movie> moviesList) {
        this.context = context;
        this.moviesList = moviesList;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieViewHolder(LayoutInflater.from(context).inflate(R.layout.movie, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder holder, final int position) {
        final Movie movie = moviesList.get(position);
        Picasso.get()
                .load(BuildConfig.API_IMG_BASE + movie.getPosterPath())
                .into(holder.poster);
        holder.title.setText(movie.getTitle());
        holder.overview.setText(movie.getOverview());
        holder.rating.setText(movie.getVoteAverage().toString());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                intent.putExtra("movie", new Gson().toJson(movie));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (moviesList == null) ? 0 : moviesList.size();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout layout;
        private ImageView poster;
        private TextView title;
        private TextView overview;
        private TextView rating;

        MovieViewHolder(View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.movie);
            poster = itemView.findViewById(R.id.poster);
            title = itemView.findViewById(R.id.movie_title);
            overview = itemView.findViewById(R.id.overview);
            rating = itemView.findViewById(R.id.rating);
        }

    }
}