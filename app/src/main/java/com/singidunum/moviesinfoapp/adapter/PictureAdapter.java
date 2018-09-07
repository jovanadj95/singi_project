package com.singidunum.moviesinfoapp.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.singidunum.moviesinfoapp.BuildConfig;
import com.singidunum.moviesinfoapp.R;
import com.singidunum.moviesinfoapp.model.api.pictures.Backdrop;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.PictureViewHolder> {

    private Context context;
    private List<Backdrop> pictureList;
    private ImageView largerPicture;

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
    public void onBindViewHolder(@NonNull final PictureViewHolder holder, int position) {
        final int currentPosition = position;
        Picasso.get()
                .load(Uri.parse(BuildConfig.API_IMG_BASE + "w342" + pictureList.get(currentPosition).getFilePath()))
                .into(holder.picture);
        holder.picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog builder = new Dialog(context);
                builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
                builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                    }
                });
                largerPicture = new ImageView(context);
                Picasso.get().load(Uri.parse(BuildConfig.API_IMG_BASE + "w1280" + pictureList.get(currentPosition).getFilePath())).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        largerPicture.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                    }
                });
                builder.addContentView(largerPicture, new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                builder.show();
            }
        });
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
