package com.udacity.uchennachukwuwa.moviesdb.ui.movieList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
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
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.udacity.uchennachukwuwa.moviesdb.R;
import com.udacity.uchennachukwuwa.moviesdb.adapters.MovieAdapter;
import com.udacity.uchennachukwuwa.moviesdb.database.remote.RetrofitApiService;
import com.udacity.uchennachukwuwa.moviesdb.database.remote.RetrofitClient;
import com.udacity.uchennachukwuwa.moviesdb.model.Movie;
import com.udacity.uchennachukwuwa.moviesdb.model.MoviesResponse;
import com.udacity.uchennachukwuwa.moviesdb.ui.movieDetail.MovieDetailActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.udacity.uchennachukwuwa.moviesdb.constant.Constant.API_KEY;
import static com.udacity.uchennachukwuwa.moviesdb.constant.Constant.INTENT_KEY;

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnItemClickListener {
    private RecyclerView mRecyclerView;
    RetrofitApiService client;
    private ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = findViewById(R.id.progressBar);
        client = RetrofitClient.getClient().create(RetrofitApiService.class);

        mRecyclerView = findViewById(R.id.recycler_movies);

        getPopular();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movies_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
        case R.id.popular:
        showProgressBar();
        getPopular();
        return true;
        case R.id.top_rated:
        showProgressBar();
        getTopRated();
        return true;
            default:
        return super.onOptionsItemSelected(item);}
    }

    private void getTopRated(){

        if (isOnline()){
        Call<MoviesResponse> call = client.getTopRated(API_KEY);
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    List<Movie> responseFromApi = response.body().getResults();
                    hideProgressBar();
                    MovieAdapter adapter = new MovieAdapter(responseFromApi, MainActivity.this);
                    mRecyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        }
        hideProgressBar();
        showSnackBar();
    }

    private void getPopular() {

        if (isOnline()){

        Call<MoviesResponse> call = client.getPopularMovies(API_KEY);
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    List<Movie> responseFromApi = response.body().getResults();
                    hideProgressBar();
                    MovieAdapter adapter = new MovieAdapter(responseFromApi, MainActivity.this);
                    mRecyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        }else {
            hideProgressBar();
            showSnackBar();
        }
    }

    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    private void showSnackBar(){
        Snackbar snackbar = Snackbar
                .make(mProgressBar, "No internet Connection! ", Snackbar.LENGTH_LONG)
                .setAction("Check settings", view -> {
                    Intent settings = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                    this.startActivity(settings);
                });
        snackbar.setActionTextColor(Color.BLACK);
        View view = snackbar.getView();
        view.setBackgroundColor(ContextCompat.getColor(this
                ,R.color.snackBarBackground));
        snackbar.setText("No internet Connection!");
        snackbar.setTextColor(Color.WHITE);
        snackbar.show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        showProgressBar();
        getPopular();
    }

    @Override
    public void onItemClick(Movie movie, ImageView imageView) {

        Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class)
                .putExtra(INTENT_KEY, movie);
            startActivity(intent);

    }
}