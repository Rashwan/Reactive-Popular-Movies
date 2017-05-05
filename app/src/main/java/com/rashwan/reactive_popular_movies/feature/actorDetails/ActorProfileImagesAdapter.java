package com.rashwan.reactive_popular_movies.feature.actorDetails;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rashwan.reactive_popular_movies.R;
import com.rashwan.reactive_popular_movies.data.model.ActorProfileImage;
import com.rashwan.reactive_popular_movies.feature.actorDetails.ActorProfileImagesAdapter.ActorProfileImageVH;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rashwan on 5/5/17.
 */

public class ActorProfileImagesAdapter extends RecyclerView.Adapter<ActorProfileImageVH> {
    private List<ActorProfileImage> profileImages;

    public ActorProfileImagesAdapter() {
        this.profileImages = new ArrayList<>();
    }

    @Override
    public ActorProfileImageVH onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_actor_image,parent,false);
        return new ActorProfileImageVH(view);
    }

    @Override
    public void onBindViewHolder(ActorProfileImageVH holder, int position) {
        ActorProfileImage profileImage = profileImages.get(position);
        Picasso.with(holder.itemView.getContext()).load(profileImage
                .getFullImagePath(ActorProfileImage.QUALITY_LOW))
                .into(holder.actorProfileImage);
    }

    @Override
    public int getItemCount() {
        return profileImages.size();
    }
    public void addProfileImages(List<ActorProfileImage> profileImages){
        this.profileImages.addAll(profileImages);
    }

    public class ActorProfileImageVH extends RecyclerView.ViewHolder{
        @BindView(R.id.actor_image) ImageView actorProfileImage;
        public ActorProfileImageVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

    }
}
