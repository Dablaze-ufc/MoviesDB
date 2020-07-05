package com.udacity.uchennachukwuwa.moviesdb.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import com.udacity.uchennachukwuwa.moviesdb.R;
import com.udacity.uchennachukwuwa.moviesdb.database.remote.RetrofitApiService;
import com.udacity.uchennachukwuwa.moviesdb.database.remote.RetrofitClient;
import com.udacity.uchennachukwuwa.moviesdb.model.Movie;
import com.udacity.uchennachukwuwa.moviesdb.model.MoviesResponse;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.udacity.uchennachukwuwa.moviesdb.constant.Constant.API_KEY;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView mRecyclerView = findViewById(R.id.recycler_movies);
        getPopular();
    }

    private void getPopular() {
        RetrofitApiService client =  RetrofitClient.getClient().create(RetrofitApiService.class);
        Call<MoviesResponse> call = client.getPopularMovies(API_KEY);
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                if (response.code() == 200){
                    assert response.body() != null;
                    List<Movie> responseFromApi = response.body().getResults();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MoviesResponse> call,  @NonNull Throwable t) {

            }
        });
    }
}