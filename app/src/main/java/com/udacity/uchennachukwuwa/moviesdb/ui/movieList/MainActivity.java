package com.udacity.uchennachukwuwa.moviesdb.ui.movieList;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.udacity.uchennachukwuwa.moviesdb.R;
import com.udacity.uchennachukwuwa.moviesdb.adapters.MovieAdapter;
import com.udacity.uchennachukwuwa.moviesdb.model.Movie;
import com.udacity.uchennachukwuwa.moviesdb.ui.movieDetail.MovieDetailActivity;

import static com.udacity.uchennachukwuwa.moviesdb.constant.Constant.INTENT_KEY;

/**
 * Created by ChukwuwaUchenna
 */

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnItemClickListener {
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private MainActivityViewModel mViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = findViewById(R.id.progressBar);
        mViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        mRecyclerView = findViewById(R.id.recycler_movies);


        mViewModel.message.observe(this, this::showToast);

        mViewModel.moviesList.observe(this, list -> {
            MovieAdapter adapter = new MovieAdapter(list, MainActivity.this);
            mRecyclerView.setAdapter(adapter);
        });

        mViewModel.online.observe(this, this::showSnackBar);
        mViewModel.state.observe(this, state -> {
            switch (state) {
                case "Loading":
                    showProgressBar();
                case "Done":
                    hideProgressBar();
                default:
                    hideProgressBar();
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movies_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.popular:
                showProgressBar();
                mViewModel.getPopular();
                return true;
            case R.id.top_rated:
                showProgressBar();
                mViewModel.getTopRated();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    private void showSnackBar(Boolean online) {

        if (!online) {
            Snackbar snackbar = Snackbar
                    .make(mProgressBar, "No internet Connection! ", Snackbar.LENGTH_LONG)
                    .setAction("Check settings", view -> {
                        Intent settings = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                        this.startActivity(settings);
                    });
            snackbar.setActionTextColor(Color.BLACK);
            View view = snackbar.getView();
            view.setBackgroundColor(ContextCompat.getColor(this
                    , R.color.snackBarBackground));
            snackbar.setText("No internet Connection!");
            snackbar.setTextColor(Color.WHITE);
            snackbar.show();
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(Movie movie, ImageView imageView) {

        Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class)
                .putExtra(INTENT_KEY, movie);
        startActivity(intent);

    }
}