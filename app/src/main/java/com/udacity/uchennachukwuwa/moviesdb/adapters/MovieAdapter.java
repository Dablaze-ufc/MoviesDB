package com.udacity.uchennachukwuwa.moviesdb.adapters;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MoviesViewHolder> {


    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder {
        public MoviesViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
