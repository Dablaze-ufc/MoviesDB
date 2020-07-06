package com.udacity.uchennachukwuwa.moviesdb.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.udacity.uchennachukwuwa.moviesdb.R;
import com.udacity.uchennachukwuwa.moviesdb.model.Movie;

import java.util.List;

import static com.udacity.uchennachukwuwa.moviesdb.constant.Constant.POSTER_BASE_URL;

/**
 * Created by ChukwuwaUchenna
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MoviesViewHolder> {
    List<Movie> movieList;
    private OnItemClickListener onItemClickListener;

    public MovieAdapter(List<Movie> movies, OnItemClickListener onItemClickListener) {
        this.movieList = movies;
        this.onItemClickListener = onItemClickListener;
    }


    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new MoviesViewHolder(layoutInflater.inflate(R.layout.list_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.bind(movie, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class MoviesViewHolder extends RecyclerView.ViewHolder {
        public MoviesViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(final Movie movie, final OnItemClickListener onItemClickListener){
            ImageView image = itemView.findViewById(R.id.imageView_movie_poster);
            String posterPathString = POSTER_BASE_URL + movie.getPosterPath();

            Picasso.get()
                    .load(posterPathString)
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
                    .into(image);

            itemView.setOnClickListener(  v -> onItemClickListener.onItemClick(movie, image));
        }
    }
    public interface OnItemClickListener {
        void onItemClick(Movie movie, ImageView imageView);
    }
}
