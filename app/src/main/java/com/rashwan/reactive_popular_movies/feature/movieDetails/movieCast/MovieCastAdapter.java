package com.rashwan.reactive_popular_movies.feature.movieDetails.movieCast;

import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rashwan.reactive_popular_movies.R;
import com.rashwan.reactive_popular_movies.common.utilities.RoundedTransformation;
import com.rashwan.reactive_popular_movies.common.utilities.RvItemClickListener;
import com.rashwan.reactive_popular_movies.data.model.Cast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rashwan on 4/21/17.
 */

public class MovieCastAdapter extends RecyclerView.Adapter<MovieCastAdapter.CastVH>{
    private List<Cast> castList ;
    private RvItemClickListener<Cast> itemClickListener;


    public MovieCastAdapter() {
        castList = new ArrayList<>(20);
    }

    public void setItemClickListener(RvItemClickListener<Cast> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
    public void removeClickListener(){
        this.itemClickListener = null;
    }

    @Override
    public CastVH onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_cast, parent, false);
        return new CastVH(view);
    }

    @Override
    public void onBindViewHolder(CastVH holder, int position) {
        final Context context = holder.itemView.getContext();
        Cast castItem = castList.get(position);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.actorProfile.setTransitionName("actorProfile_" + castItem.id());
        }
        holder.castItem = castItem;
        holder.actorCharacter.setText(context.getString(R.string.cast_character_name,castItem.character()));
        holder.actorName.setText(castItem.name());
        if (castItem.profilePath() != null) {
            Picasso.with(context).load(castItem.getFullProfilePath(Cast.QUALITY_LOW))
                    .placeholder(R.drawable.ic_account_circle)
                    .transform(
                            new RoundedTransformation(ContextCompat.getColor(context, android.R.color.white)))
                    .into(holder.actorProfile);
        }
        else {
            Picasso.with(context).load(R.drawable.ic_account_circle).into(holder.actorProfile);
        }
    }

    @Override
    public int getItemCount() {
        return castList.size();
    }
    public void addCast(List<Cast> cast){castList.addAll(cast);}

    public class CastVH extends RecyclerView.ViewHolder{
        @BindView(R.id.cast_item_container) LinearLayout castItemContainer;
        @BindView(R.id.image_actor_profile) ImageView actorProfile;
        @BindView(R.id.text_actor_name) TextView actorName;
        @BindView(R.id.text_actor_character) TextView actorCharacter;
        private Cast castItem;
        public CastVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
        @OnClick(R.id.cast_item_container)
        void onCastItemClicked(View view){
            if (itemClickListener!= null) {
                ImageView profile = ButterKnife.findById(view, R.id.image_actor_profile);
                itemClickListener.onItemClicked(castItem,profile);
            }
        }
    }

}
