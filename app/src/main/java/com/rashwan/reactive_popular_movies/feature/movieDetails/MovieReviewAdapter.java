package com.rashwan.reactive_popular_movies.feature.movieDetails;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rashwan.reactive_popular_movies.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rashwan on 7/4/16.
 */

public class MovieReviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    String[] reviews = {"asd", "asd", "wetrfd", "dfgyr", "gfdtyr", "sdfrt"};

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_review, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return reviews.length;
    }

    static class ReviewViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.review_author)
        TextView reviewAuthor;
        @BindView(R.id.review_content)
        TextView reviewContent;

        public ReviewViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }
}
