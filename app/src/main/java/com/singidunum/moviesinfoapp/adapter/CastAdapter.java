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

import com.singidunum.moviesinfoapp.BuildConfig;
import com.singidunum.moviesinfoapp.R;
import com.singidunum.moviesinfoapp.activity.ActorDetailsActivity;
import com.singidunum.moviesinfoapp.model.api.credits.Cast;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastViewHolder> {

    private Context context;
    private List<Cast> castList;

    public CastAdapter(Context context, List<Cast> castList) {
        this.context = context;
        this.castList = castList;
    }

    @NonNull
    @Override
    public CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CastViewHolder(LayoutInflater.from(context).inflate(R.layout.movie_details_actors_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CastViewHolder holder, int position) {
        final Cast cast = castList.get(position);
        if (cast.getProfilePath() != null) {
            Picasso.get()
                    .load(BuildConfig.API_IMG_BASE + "w185" + cast.getProfilePath())
                    .into(holder.actorPicture);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ActorDetailsActivity.class);
                    intent.putExtra("actorId", cast.getId());
                    context.startActivity(intent);
                }
            });
        } else {
            holder.actorPicture.setImageResource(R.drawable.no_image_available);
        }
        holder.actorName.setText(cast.getName());
        holder.character.setText(cast.getCharacter());
    }

    @Override
    public int getItemCount() {
        return (castList == null) ? 0 : castList.size();
    }

    static class CastViewHolder extends RecyclerView.ViewHolder {

        private ImageView actorPicture;
        private TextView actorName;
        private TextView character;

        CastViewHolder(View itemView) {
            super(itemView);
            actorPicture = itemView.findViewById(R.id.actor_picture);
            actorName = itemView.findViewById(R.id.actor_name);
            character = itemView.findViewById(R.id.character);
        }
    }
}
