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

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rashwan on 7/4/16.
 */

public class MovieTrailersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Trailer> trailers;
    @Inject
    public MovieTrailersAdapter() {
        trailers = new ArrayList<>();
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
        Trailer trailer = trailers.get(position);
        ((MovieTrailerViewHolder)holder).trailerName.setText(trailer.name());
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    public void setTrailers(List<Trailer> trailers){
        this.trailers = trailers;
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
