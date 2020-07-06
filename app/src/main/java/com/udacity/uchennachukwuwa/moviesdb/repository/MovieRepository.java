package com.udacity.uchennachukwuwa.moviesdb.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.udacity.uchennachukwuwa.moviesdb.database.local.FavouriteMovieDAO;
import com.udacity.uchennachukwuwa.moviesdb.database.local.MovieDatabase;
import com.udacity.uchennachukwuwa.moviesdb.database.remote.RetrofitApiService;
import com.udacity.uchennachukwuwa.moviesdb.database.remote.RetrofitClient;
import com.udacity.uchennachukwuwa.moviesdb.model.Movie;
import com.udacity.uchennachukwuwa.moviesdb.model.MoviesResponse;
import com.udacity.uchennachukwuwa.moviesdb.model.ReviewResponse;
import com.udacity.uchennachukwuwa.moviesdb.model.TrailerResponse;

import java.util.List;

import retrofit2.Call;

/**
 * Created by ChukwuwaUchenna
 */

public class MovieRepository {
    private RetrofitApiService mService;
    private FavouriteMovieDAO mDAO;

    public MovieRepository(Application application) {
        MovieDatabase database = MovieDatabase.getDatabase(application);
        mDAO = database.mMovieDao();
        mService = RetrofitClient.getClient().create(RetrofitApiService.class);
    }

    public LiveData<List<Movie>> getFavoriteMovies() {
        return mDAO.getFavoriteMovies();
    }

    public void addToFavourite(Movie movie){

        InsertAsyncTask insertAsyncTask =  new InsertAsyncTask(mDAO);
        insertAsyncTask.execute(movie);
    }

    public void removeFromFavourite(Movie movie){

        DeleteAsyncTask deleteAsyncTask =  new DeleteAsyncTask(mDAO);
        deleteAsyncTask.execute(movie);
    }

    public Call<MoviesResponse> getPopularMovies(String apiKey) {
        return mService.getPopularMovies(apiKey);
    }


    public Call<MoviesResponse> getRatedMovies(String apiKey) {
        return mService.getTopRated(apiKey);
    }

    public Call<ReviewResponse> getReviews(int id, String apiKey){
       return mService.getMovieReviews(id,apiKey);
    }

    public Call<TrailerResponse> getTrailers(int id, String apiKey){
        return mService.getMovieTrailers(id,apiKey);
    }

    private static class InsertAsyncTask extends AsyncTask<Movie, Void, Void> {
        private FavouriteMovieDAO sDAO;
        public InsertAsyncTask(FavouriteMovieDAO mDAO) {
            this.sDAO = mDAO;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            sDAO.insertFavoriteMovie(movies[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Movie, Void, Void> {
        private FavouriteMovieDAO sDAO;
        public DeleteAsyncTask(FavouriteMovieDAO mDAO) {
            this.sDAO = mDAO;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            sDAO.deleteFavoriteMovie(movies[0]);
            return null;
        }
    }
}
