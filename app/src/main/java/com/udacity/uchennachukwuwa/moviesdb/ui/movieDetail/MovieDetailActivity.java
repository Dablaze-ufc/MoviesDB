package com.udacity.uchennachukwuwa.moviesdb.ui.movieDetail;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;
import com.udacity.uchennachukwuwa.moviesdb.R;
import com.udacity.uchennachukwuwa.moviesdb.model.Movie;

import static com.udacity.uchennachukwuwa.moviesdb.constant.Constant.INTENT_KEY;
import static com.udacity.uchennachukwuwa.moviesdb.constant.Constant.POSTER_BASE_URL;

public class MovieDetailActivity extends AppCompatActivity {
    ImageView posterImage;
    TextView movieTitle, movieRating, movieOverview, movieReleaseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        posterImage = findViewById(R.id.image_poster);
        movieTitle = findViewById(R.id.text_tittle);
        movieOverview = findViewById(R.id.text_overview);
        movieRating = findViewById(R.id.text_rating);
        movieReleaseDate = findViewById(R.id.text_release_year);

        Intent intentResult = getIntent();
        Movie movie = intentResult.getParcelableExtra(INTENT_KEY);
        if(movie !=  null){
            String voteAverage = movie.getVoteAverage() + "/" + "10";
            movieTitle.setText(movie.getOriginalTitle());
            movieOverview.setText(movie.getOverview());
            movieReleaseDate.setText(movie.getReleaseDate());
            movieRating.setText(voteAverage);

            String posterString = POSTER_BASE_URL + movie.getPosterPath();

            Picasso.get()
                    .load(posterString)
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
                    .into(posterImage);
        }

    }
}