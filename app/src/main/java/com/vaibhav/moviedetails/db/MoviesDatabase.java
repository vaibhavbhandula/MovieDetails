package com.vaibhav.moviedetails.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.vaibhav.moviedetails.dao.MovieDao;
import com.vaibhav.moviedetails.data.Movie;

/**
 * @author Vaibhav Bhandula on 09/10/18.
 */
@Database(entities = {Movie.class}, version = 1)
public abstract class MoviesDatabase extends RoomDatabase {

    private static final String DB_NAME = "movieDatabase.db";
    private static volatile MoviesDatabase instance;

    public static synchronized MoviesDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static MoviesDatabase create(final Context context) {
        return Room.databaseBuilder(
                context,
                MoviesDatabase.class,
                DB_NAME).build();
    }

    public abstract MovieDao getEventDao();

}
