package com.udacity.uchennachukwuwa.moviesdb.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.uchennachukwuwa.moviesdb.R;
import com.udacity.uchennachukwuwa.moviesdb.model.ReviewResult;

import java.util.List;

/**
 * Created by ChukwuwaUchenna
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder> {

    private List<ReviewResult> reviews;
    private OnItemClickListener mClickListener;
    @NonNull
    @Override
    public ReviewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ReviewsViewHolder(inflater.inflate(R.layout.list_item_review, parent, false));
    }


    @Override
    public void onBindViewHolder(ReviewsViewHolder holder, int position) {
        ReviewResult item = reviews.get(position);
        holder.bind(item, mClickListener);
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }


    public ReviewsAdapter(List<ReviewResult> reviews, OnItemClickListener clickListener) {
        this.reviews = reviews;
        this.mClickListener = clickListener;
    }

    static class ReviewsViewHolder extends RecyclerView.ViewHolder {
        ReviewsViewHolder(View itemView) {
            super(itemView);
        }

        void bind(final ReviewResult review, OnItemClickListener listener) {
            TextView reviewAuthor = itemView.findViewById(R.id.text_author);
            TextView reviewContent = itemView.findViewById(R.id.text_content);
            reviewAuthor.setText(review.getAuthor());
            reviewContent.setText(review.getContent());
            itemView.setOnClickListener(v -> listener.onReviewItemClick(review.getUrl()));
        }
    }


    public interface OnItemClickListener {
        void onReviewItemClick(String url);
    }
}
