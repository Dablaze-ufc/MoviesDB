package com.udacity.uchennachukwuwa.moviesdb.database.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.udacity.uchennachukwuwa.moviesdb.model.Movie;


/**
 * Created by ChukwuwaUchenna
 */


@Database(entities = {Movie.class}, exportSchema = false, version = 1)
public abstract class MovieDatabase extends RoomDatabase {
    public abstract FavouriteMovieDAO mMovieDao();

    private static final Object LOCK = new Object();
    private static volatile MovieDatabase INSTANCE;

    public static MovieDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            MovieDatabase.class,
                            "movie_database"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
}
