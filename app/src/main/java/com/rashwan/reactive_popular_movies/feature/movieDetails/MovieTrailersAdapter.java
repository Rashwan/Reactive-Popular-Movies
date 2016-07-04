package com.rashwan.reactive_popular_movies.feature.movieDetails;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rashwan.reactive_popular_movies.R;
import com.rashwan.reactive_popular_movies.model.Trailer;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rashwan on 7/4/16.
 */

public class MovieTrailersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Trailer[] trailers = new Trailer[]{new Trailer("qwe", "trailer1"), new Trailer("asd", "trailer2")};

    @Inject
    public MovieTrailersAdapter() {
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_trailer, parent, false);
        return new MovieTrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        Trailer trailer = trailers[position];
        ((MovieTrailerViewHolder)holder).trailerName.setText(trailer.getName());
    }

    @Override
    public int getItemCount() {
        return trailers.length;
    }

    static class MovieTrailerViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.youtube_thumbnail)
        ImageView youtubeThumbnail;
        @BindView(R.id.trailer_name)
        TextView trailerName;

         MovieTrailerViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }
}
