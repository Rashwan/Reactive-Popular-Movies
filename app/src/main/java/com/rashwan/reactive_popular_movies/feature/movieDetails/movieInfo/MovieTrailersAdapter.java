package com.rashwan.reactive_popular_movies.feature.movieDetails.movieInfo;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.rashwan.reactive_popular_movies.R;
import com.rashwan.reactive_popular_movies.data.model.Trailer;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rashwan on 7/4/16.
 */

public class MovieTrailersAdapter extends RecyclerView.Adapter<MovieTrailersAdapter.MovieTrailerViewHolder> {
    private List<Trailer> trailers;
    private MovieTrailersAdapter.ClickListener mClickListener;
    public MovieTrailersAdapter() {
        trailers = new ArrayList<>();
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
                .into(holder.youtubeThumbnail, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.buttonPlay.setVisibility(View.VISIBLE);
                    }
                    @Override
                    public void onError() {

                    }
                });
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    public void setTrailers(List<Trailer> trailers){
        this.trailers = trailers;
    }

    public Boolean isEmpty(){return this.trailers.isEmpty();}

    public Trailer getTrailer(int position){
        return this.trailers.get(position);
    }

    public void setClickListener(ClickListener ClickListener) {
        this.mClickListener = ClickListener;
    }

    public class MovieTrailerViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.image_youtube_trailer) ImageView youtubeThumbnail;
        @BindView(R.id.text_trailer_name) TextView trailerName;
        @BindView(R.id.button_play_trailer) ImageButton buttonPlay;
        Trailer mTrailer;

         MovieTrailerViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }

        @OnClick({R.id.image_youtube_trailer,R.id.button_play_trailer})
        public void onPlayTrailerClicked(){
            mClickListener.onTrailerClicked(mTrailer.getFullYoutubeUri());
        }
    }
    public interface ClickListener {
        void onTrailerClicked(Uri fullYoutubeUri);
    }
}
