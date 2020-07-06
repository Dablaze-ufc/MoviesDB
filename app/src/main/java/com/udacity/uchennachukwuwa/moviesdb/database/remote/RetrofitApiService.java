package com.udacity.uchennachukwuwa.moviesdb.database.remote;

import com.udacity.uchennachukwuwa.moviesdb.model.MoviesResponse;
import com.udacity.uchennachukwuwa.moviesdb.model.ReviewResponse;
import com.udacity.uchennachukwuwa.moviesdb.model.TrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ChukwuwaUchenna
 */
public interface RetrofitApiService {
    @GET("movie/popular")
    Call<MoviesResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRated(@Query("api_key") String apiKey);

    @GET("movie/{movie_id}/videos")
    Call<TrailerResponse> getMovieTrailers(@Path("movie_id") int id, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/reviews")
    Call<ReviewResponse> getMovieReviews(@Path("movie_id") int id, @Query("api_key") String apiKey);


}
