package com.udacity.uchennachukwuwa.moviesdb.ui.movieDetail;


import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.udacity.uchennachukwuwa.moviesdb.model.Movie;
import com.udacity.uchennachukwuwa.moviesdb.model.ReviewResponse;
import com.udacity.uchennachukwuwa.moviesdb.model.ReviewResult;
import com.udacity.uchennachukwuwa.moviesdb.model.TrailerResponse;
import com.udacity.uchennachukwuwa.moviesdb.model.TrailerResult;
import com.udacity.uchennachukwuwa.moviesdb.repository.MovieRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.udacity.uchennachukwuwa.moviesdb.constant.Constant.API_KEY;

/**
 * Created by ChukwuwaUchenna
 */

public class MovieDetailViewModel extends AndroidViewModel {
    private MovieRepository mRepository;
    private Application app;
    private MutableLiveData<List<ReviewResult>> _reviewsList = new MutableLiveData<>();
    private MutableLiveData<List<TrailerResult>> _trailersList = new MutableLiveData<>();
    private MutableLiveData<String> _message = new MutableLiveData<>();
    private MutableLiveData<Boolean> _online = new MutableLiveData<>();
    public LiveData<String> message = _message;
    public LiveData<Boolean> online = _online;
    public LiveData<List<ReviewResult>> reviewsList = _reviewsList;
    public LiveData<List<TrailerResult>> trailerList =_trailersList;

    public MovieDetailViewModel(@NonNull Application application) {
        super(application);
        mRepository = new MovieRepository(application);
        this.app = application;
        _online.setValue(isOnline());
    }

    public void addToFavourites(Movie movie){
        mRepository.addToFavourite(movie);
    }

    public void removeFromFavourites(Movie movie){
        mRepository.removeFromFavourite(movie);
    }

    public void getReviews(int id) {
        if (isOnline()) {
            mRepository.getReviews(id, API_KEY).enqueue(new Callback<ReviewResponse>() {
                @Override
                public void onResponse(@NonNull Call<ReviewResponse> call, @NonNull Response<ReviewResponse> response) {
                    if (response.code() == 200) {
                        assert response.body() != null;
                        _reviewsList.setValue(response.body().getResults());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ReviewResponse> call, @NonNull Throwable t) {
                    _message.setValue(t.getLocalizedMessage());
                }
            });
        }
    }


    public void getTrailers(int id) {
        if (isOnline()) {
            mRepository.getTrailers(id, API_KEY).enqueue(new Callback<TrailerResponse>() {
                @Override
                public void onResponse(@NonNull Call<TrailerResponse> call, @NonNull Response<TrailerResponse> response) {
                    if (response.code() == 200) {
                        assert response.body() != null;
                        _trailersList.setValue(response.body().getTrailerResults());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<TrailerResponse> call, @NonNull Throwable t) {
                    _message.setValue(t.getLocalizedMessage());
                }
            });
        }
    }
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) app.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
