package com.vaibhav.moviedetails.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.vaibhav.moviedetails.data.Movie;

import java.util.List;

/**
 * @author Vaibhav Bhandula on 09/10/18.
 */
@Dao
public interface MovieDao {

    @Query("Select * from movie") List<Movie> getAllMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE) void insert(Movie... movies);

    @Delete void delete(Movie... movies);
}
