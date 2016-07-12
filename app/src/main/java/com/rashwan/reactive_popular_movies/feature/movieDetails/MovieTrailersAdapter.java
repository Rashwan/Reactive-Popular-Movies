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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rashwan on 7/4/16.
 */

public class MovieTrailersAdapter extends RecyclerView.Adapter<MovieTrailersAdapter.MovieTrailerViewHolder> {
    List<Trailer> trailers;
    private MovieTrailersAdapter.ClickListener mClickListener;
    @Inject
    public MovieTrailersAdapter() {
        trailers = new ArrayList<>();
    }

    public void setClickListener(ClickListener ClickListener) {
        this.mClickListener = ClickListener;
    }

    @Override
    public MovieTrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_trailer, parent, false);
        return new MovieTrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieTrailerViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        Trailer trailer = trailers.get(position);
        holder.mTrailer = trailer;

        holder.trailerName.setText(trailer.name());
        Picasso.with(context).load(trailer.getTrailerThumbnail()).centerCrop().fit()
                .into(holder.youtubeThumbnail);
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    public void setTrailers(List<Trailer> trailers){
        this.trailers = trailers;
    }

    public class MovieTrailerViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.trailer_youtube_thumbnail)
        ImageView youtubeThumbnail;
        @BindView(R.id.trailer_name)
        TextView trailerName;
        Trailer mTrailer;

         MovieTrailerViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }

        @OnClick({R.id.trailer_name,R.id.trailer_youtube_thumbnail})
        public void trailerClicked(){
            mClickListener.onTrailerClicked(mTrailer);
        }
    }
    public interface ClickListener {
        void onTrailerClicked(Trailer trailer);
    }
}
