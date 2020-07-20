package com.udacity.uchennachukwuwa.moviesdb.ui.movieList;


import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.udacity.uchennachukwuwa.moviesdb.R;
import com.udacity.uchennachukwuwa.moviesdb.model.Movie;
import com.udacity.uchennachukwuwa.moviesdb.model.MoviesResponse;
import com.udacity.uchennachukwuwa.moviesdb.repository.MovieRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.udacity.uchennachukwuwa.moviesdb.constant.Constant.API_KEY;

/**
 * Created by ChukwuwaUchenna
 */

public class MainActivityViewModel extends AndroidViewModel {
    private MovieRepository mRepository;
    private Application app;
    private MutableLiveData<List<Movie>> _moviesList = new MutableLiveData<>();
    private MutableLiveData<List<Movie>> _favMoviesList = new MutableLiveData<>();

    private MutableLiveData<String> _message = new MutableLiveData<>();
    private MutableLiveData<Boolean> _online = new MutableLiveData<>();
    private MutableLiveData<String> _state = new MutableLiveData<>();
    public LiveData<List<Movie>> moviesList = _moviesList;
    public LiveData<String> message = _message;
    public LiveData<Boolean> online = _online;
    public LiveData<String> state = _state;


    public MainActivityViewModel(@NonNull Application application) {
        super(application);
//        _state.setValue(R.string.loading_progress);
        mRepository = new MovieRepository(application);
        this.app = application;
        _online.setValue(isOnline());
        getPopular();

    }

    public LiveData<List<Movie>> getFavourite() {
       if (mRepository.getFavoriteMovies() == null){
          return  _favMoviesList= new MutableLiveData<>();
       }else{
           return mRepository.getFavoriteMovies();
       }
    }


    public void getTopRated() {
        _state.setValue(app.getString(R.string.loading_progress));

        if (isOnline()) {

            mRepository.getRatedMovies(API_KEY).enqueue(new Callback<MoviesResponse>() {
                @Override
                public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                    if (response.code() == 200) {
                        _state.setValue(app.getString(R.string.done_state));
                        assert response.body() != null;
                        _moviesList.setValue(response.body().getResults());


                    }
                }

                @Override
                public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {
                    _message.setValue(t.getLocalizedMessage());
                    _state.setValue(app.getString(R.string.done_state));
                }
            });
        } else {
            _online.setValue(false);
        }
    }


    public void getPopular() {
        _state.setValue(app.getString(R.string.loading_progress));

        if (isOnline()) {

            mRepository.getPopularMovies(API_KEY).enqueue(new Callback<MoviesResponse>() {
                @Override
                public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                    if (response.code() == 200) {
                        _state.setValue(app.getString(R.string.done_state));


                        assert response.body() != null;
                        _moviesList.setValue(response.body().getResults());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {
                    _message.setValue(t.getLocalizedMessage());
                }
            });
        } else {
            _state.setValue(app.getString(R.string.done_state));
            _online.setValue(false);
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
