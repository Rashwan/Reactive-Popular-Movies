package com.rashwan.reactive_popular_movies.feature.movieDetails.movieReviews;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rashwan.reactive_popular_movies.R;
import com.rashwan.reactive_popular_movies.data.model.Review;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rashwan on 7/4/16.
 */

public class MovieReviewsAdapter extends RecyclerView.Adapter<MovieReviewsAdapter.ReviewViewHolder> {
    private List<Review> reviewList;

    public MovieReviewsAdapter() {
        reviewList = new ArrayList<>();
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_review, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        Review review = reviewList.get(position);
        holder.reviewAuthor.setText(review.author());
        holder.reviewContent.setText(review.content());

    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }
    public void addReviews(List<Review> reviews){
        reviewList.addAll(reviews);
    }
    public boolean isEmpty(){return reviewList.isEmpty();}


     class ReviewViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_review_author) TextView reviewAuthor;
        @BindView(R.id.text_review_content) TextView reviewContent;

        public ReviewViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }
}
