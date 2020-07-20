package com.udacity.uchennachukwuwa.moviesdb.ui.movieList;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
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
        mViewModel.state.observe(this, state ->{
            if (TextUtils.equals(state,getString(R.string.loading_progress))){
                showProgressBar();
            }else {
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
            case R.id.favourites:
                getFavourite();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getFavourite() {
        mViewModel.getFavourite().observe(this,list ->{
            MovieAdapter adapter = new MovieAdapter(list, MainActivity.this);
            mRecyclerView.setAdapter(adapter);
        });
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
                    .make(mProgressBar, getString(R.string.no_internet), Snackbar.LENGTH_LONG)
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
    public void onItemClick(Movie movie, ImageView imageView) {

        Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class)
                .putExtra(INTENT_KEY, movie);
        Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                MainActivity.this,
                imageView,
                imageView.getTransitionName()
        ).toBundle();
        startActivity(intent, bundle);
    }



    private void onChanged(int state) {
        switch (state) {
            case R.string.loading_progress:
                showProgressBar();
            case R.string.done_state:
                hideProgressBar();
            default:
                hideProgressBar();
        }
    }
}