package com.udacity.uchennachukwuwa.moviesdb.database.remote;

import com.udacity.uchennachukwuwa.moviesdb.model.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ChukwuwaUchenna
 */
public interface RetrofitApiService {
    @GET("movie/popular")
    Call<MoviesResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRated(@Query("api_key") String apiKey);


}
