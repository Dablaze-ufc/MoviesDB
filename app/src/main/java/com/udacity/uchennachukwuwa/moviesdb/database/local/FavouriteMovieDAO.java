package com.udacity.uchennachukwuwa.moviesdb.database.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.udacity.uchennachukwuwa.moviesdb.model.Movie;

import java.util.List;

/**
 * Created by ChukwuwaUchenna
 */
@Dao
public interface FavouriteMovieDAO {

    /**
     * Created by ChukwuwaUchenna
     */

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void insertFavoriteMovie(Movie movie);

        @Query("SELECT * from movie_table ORDER BY id ASC")
        LiveData<List<Movie>> getFavoriteMovies();

        @Delete
        void deleteFavoriteMovie(Movie movie);

}
