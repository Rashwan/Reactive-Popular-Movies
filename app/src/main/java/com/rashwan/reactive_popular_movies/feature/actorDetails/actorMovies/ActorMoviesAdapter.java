package com.rashwan.reactive_popular_movies.feature.actorDetails.actorMovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rashwan.reactive_popular_movies.R;
import com.rashwan.reactive_popular_movies.common.utilities.Utilities;
import com.rashwan.reactive_popular_movies.data.model.ActorMovie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rashwan on 5/12/17.
 */

public class ActorMoviesAdapter extends RecyclerView.Adapter<ActorMoviesAdapter.ActorMoviesVH> {

    private List<ActorMovie> actorMovies;
    private Context context;

    public ActorMoviesAdapter() {
        this.actorMovies = new ArrayList<>();
    }

    @Override
    public ActorMoviesVH onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_actor_movie, parent, false);
        return new ActorMoviesVH(view);
    }

    @Override
    public void onBindViewHolder(ActorMoviesVH holder, int position) {
        context = holder.itemView.getContext();
        ActorMovie actorMovie = actorMovies.get(position);
        if (!actorMovie.character().isEmpty()) {
            holder.actorMoviecharacter.setText(context.getString(R.string.cast_character_name
                    , actorMovie.character()));
        }else {
            holder.actorMoviecharacter.setText(context.getString(R.string.placeholder_na));
        }
        holder.actorMovieTitle.setText(actorMovie.title());
        if (actorMovie.releaseDate()!= null) {
            holder.actorMovieReleaseDate.setText(Utilities.getFormattedDate(actorMovie.releaseDate()
                    , Utilities.MONTH_YEAR_DATE_FORMAT));
        }else {
            holder.actorMovieReleaseDate.setText(context.getString(R.string.placeholder_na));
        }
        Picasso.with(context)
                .load(Utilities.getFullPosterPath(
                        context,actorMovie.posterPath(),Utilities.QUALITY_LOW))
                .error(R.color.colorPrimaryDark)
                .into(holder.actorMoviesPoster);
    }

    @Override
    public int getItemCount() {
        return actorMovies.size();
    }
    public void addActorMovies(List<ActorMovie> actorMovieList){
        actorMovies.addAll(actorMovieList);
    }

    public class ActorMoviesVH extends RecyclerView.ViewHolder {
        @BindView(R.id.actor_movie_poster) ImageView actorMoviesPoster;
        @BindView(R.id.actor_movie_title) TextView actorMovieTitle;
        @BindView(R.id.actor_movie_character) TextView actorMoviecharacter;
        @BindView(R.id.actor_movie_release_date) TextView actorMovieReleaseDate;

        public ActorMoviesVH(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}