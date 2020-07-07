package com.udacity.uchennachukwuwa.moviesdb.ui.movieDetail;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import com.udacity.uchennachukwuwa.moviesdb.R;
import com.udacity.uchennachukwuwa.moviesdb.adapters.ReviewsAdapter;
import com.udacity.uchennachukwuwa.moviesdb.adapters.TrailersAdapter;
import com.udacity.uchennachukwuwa.moviesdb.model.Movie;

import static com.udacity.uchennachukwuwa.moviesdb.constant.Constant.INTENT_KEY;
import static com.udacity.uchennachukwuwa.moviesdb.constant.Constant.POSTER_BASE_URL;
import static com.udacity.uchennachukwuwa.moviesdb.constant.Constant.PREF_KEY;
import static com.udacity.uchennachukwuwa.moviesdb.constant.Constant.YOUTUBE_BASE_URL;

/**
 * Created by ChukwuwaUchenna
 */

public class MovieDetailActivity extends AppCompatActivity  implements ReviewsAdapter.OnItemClickListener, TrailersAdapter.OnItemClickListener {
    private MovieDetailViewModel mViewModel;
    private RecyclerView mTrailersRecyclerView, mReviewsRecyclerView;
    private MaterialButton mButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);


        mViewModel = new ViewModelProvider(this).get(MovieDetailViewModel.class);

        mTrailersRecyclerView = findViewById(R.id.trailer_recyclerview);
        mReviewsRecyclerView = findViewById(R.id.review_recyclerview);
        ImageView posterImage = findViewById(R.id.image_poster);
        TextView movieTitle = findViewById(R.id.text_tittle);
        TextView movieOverview = findViewById(R.id.text_overview);
        TextView movieRating = findViewById(R.id.text_rating);
        TextView movieReleaseDate = findViewById(R.id.text_release_year);
        mButton = findViewById(R.id.favorite_button);

        mViewModel.message.observe(this,this::showToast);

        mViewModel.online.observe(this, this::showSnackBar);
        Intent intentResult = getIntent();
        Movie movie = intentResult.getParcelableExtra(INTENT_KEY);
        if (movie != null) {
            mViewModel.getReviews(movie.getId());
            mViewModel.getTrailers(movie.getId());
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

            SharedPreferences preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            if (preferences.getBoolean(String.valueOf(movie.getId()), false)) {
                // Already a favorite
                mButton.setText(getString(R.string.remove_button_text_favourite));
            } else {
                mButton.setText(getString(R.string.add_button_text_favourite));
            }

            mButton.setOnClickListener(view ->{
                if (preferences.getBoolean(String.valueOf(movie.getId()), false)){
                    //Movie already exist in the favourites List
                    mButton.setText(getString(R.string.remove_button_text_favourite));
                    mViewModel.removeFromFavourites(movie);
                    editor.putBoolean(String.valueOf(movie.getId()),false);
                    Toast.makeText(this, getString(R.string.toast_message_removed), Toast.LENGTH_SHORT).show();
                }else {
                    //Add movie to favourite
                    mButton.setText(getString(R.string.add_button_text_favourite));
                    mViewModel.addToFavourites(movie);
                    editor.putBoolean(String.valueOf(movie.getId()), true);
                    Toast.makeText(this, getString(R.string.toast_message_added), Toast.LENGTH_SHORT).show();
                }
                finish();
                editor.apply();
            });

            mViewModel.reviewsList.observe(this, reviews ->{
                ReviewsAdapter adapter =  new ReviewsAdapter(reviews,MovieDetailActivity.this);
                mReviewsRecyclerView.setAdapter(adapter);
            });

            mViewModel.trailerList.observe(this, trailers -> {
                TrailersAdapter adapter = new TrailersAdapter(trailers, MovieDetailActivity.this);
                mTrailersRecyclerView.setAdapter(adapter);
            });
        }
    }
        private void showSnackBar(Boolean online) {

            if (!online) {
                Snackbar snackbar = Snackbar
                        .make(mReviewsRecyclerView, getString(R.string.no_internet), Snackbar.LENGTH_LONG)
                        .setAction(R.string.check_settings, view -> {
                            Intent settings = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                            this.startActivity(settings);
                        });
                snackbar.setActionTextColor(Color.BLACK);
                View view = snackbar.getView();
                view.setBackgroundColor(ContextCompat.getColor(this
                        , R.color.snackBarBackground));
                snackbar.setText(getString(R.string.no_internet));
                snackbar.setTextColor(Color.WHITE);
                snackbar.show();
            }
        }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReviewItemClick(String url) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);

    }

    @Override
    public void onTrailerItemClick(String key) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        String url = YOUTUBE_BASE_URL + key;
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}