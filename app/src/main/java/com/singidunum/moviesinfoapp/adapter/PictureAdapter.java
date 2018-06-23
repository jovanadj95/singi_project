package com.singidunum.moviesinfoapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.singidunum.moviesinfoapp.BuildConfig;
import com.singidunum.moviesinfoapp.R;
import com.singidunum.moviesinfoapp.model.api.pictures.Backdrop;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.PictureViewHolder> {

    private Context context;
    private List<Backdrop> pictureList;

    public PictureAdapter(Context context, List<Backdrop> pictureList) {
        this.context = context;
        this.pictureList = pictureList;
    }

    @NonNull
    @Override
    public PictureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PictureAdapter.PictureViewHolder(LayoutInflater.from(context).inflate(R.layout.movie_details_picture_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PictureViewHolder holder, int position) {
        Backdrop backdrop = pictureList.get(position);
        Picasso.get()
                .load(BuildConfig.API_IMG_BASE + backdrop.getFilePath())
                .into(holder.picture);
    }

    @Override
    public int getItemCount() {
        return (pictureList == null) ? 0 : pictureList.size();
    }

    static class PictureViewHolder extends RecyclerView.ViewHolder {
        private ImageView picture;

        PictureViewHolder(View itemView) {
            super(itemView);
            picture = itemView.findViewById(R.id.picture_item);
        }
    }
}
