package com.singidunum.moviesinfoapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.singidunum.moviesinfoapp.BuildConfig;
import com.singidunum.moviesinfoapp.R;
import com.singidunum.moviesinfoapp.activity.MovieDetailsActivity;
import com.singidunum.moviesinfoapp.model.api.movies.Movies;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int MOVIE_HOLDER = 1;
    private final int PROGRESS_HOLDER = 2;
    private final int EMPTY_VIEW = 3;
    public static final String TAG = "MoviesAdapter";

    private List<Movies> moviesList;
    private int page = 1;
    private int progress = 1;
    private NavigationListener navigationListener;

    public MoviesAdapter(NavigationListener navigationListener, List<Movies> moviesList) {
        this.moviesList = moviesList;
        this.navigationListener = navigationListener;
    }

    public void updateData(List<Movies> moviesList, int progress) {
        this.moviesList.addAll(moviesList);
        this.progress = progress;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case MOVIE_HOLDER:
                return new MovieViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.movie, parent, false));
            case PROGRESS_HOLDER:
                return new ProgressViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_bar_list_item, parent, false));
            case EMPTY_VIEW:
                return new EmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_list_item, parent, false));
        }
        return new EmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        if (position == 0 && (moviesList == null || moviesList.size() == 0)) {
            return;
        }
        if (position < moviesList.size()) {
            MovieViewHolder movieViewHolder = (MovieViewHolder) holder;
            final Context context = movieViewHolder.title.getContext();
            final Movies movies = moviesList.get(position);
            Picasso.get()
                    .load(BuildConfig.API_IMG_BASE + "w185" + movies.getPosterPath())
                    .into(movieViewHolder.poster);
            movieViewHolder.title.setText(movies.getTitle() + " (" + movies.getReleaseDate().substring(0, movies.getReleaseDate().indexOf("-")) + ")");
            movieViewHolder.overview.setText(movies.getOverview());
            movieViewHolder.rating.setText(String.valueOf(movies.getVoteAverage()));
            movieViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MovieDetailsActivity.class);
                    intent.putExtra("source", TAG);
                    intent.putExtra("movie", new Gson().toJson(movies));
                    context.startActivity(intent);
                }
            });
            return;
        }
        pagination();
    }

    private void pagination() {
        navigationListener.onPagination(++page);
    }

    @Override
    public int getItemCount() {
        return (moviesList == null) ? 0 : moviesList.size() + progress;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        private ImageView poster;
        private TextView title;
        private TextView overview;
        private TextView rating;

        MovieViewHolder(View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.poster);
            title = itemView.findViewById(R.id.movie_title);
            overview = itemView.findViewById(R.id.overview);
            rating = itemView.findViewById(R.id.rating);
        }
    }

    public class ProgressViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;

        ProgressViewHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    public class EmptyViewHolder extends RecyclerView.ViewHolder {

        EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0 && (moviesList == null || moviesList.size() == 0))
            return EMPTY_VIEW;
        if (position < moviesList.size()) {
            return MOVIE_HOLDER;
        }
        return PROGRESS_HOLDER;
    }

    public interface NavigationListener {
        void onPagination(int page);
    }
}