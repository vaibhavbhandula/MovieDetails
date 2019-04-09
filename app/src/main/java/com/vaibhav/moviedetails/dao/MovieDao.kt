package com.vaibhav.moviedetails.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.vaibhav.moviedetails.data.Movie
import io.reactivex.Flowable
import io.reactivex.Maybe


/**
 * @author Vaibhav Bhandula on 08/04/19.
 */
@Dao
interface MovieDao {

    @Query("Select * from movie") fun getAllMovies(): Flowable<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE) fun insert(vararg movies: Movie)

    @Delete fun delete(vararg movies: Movie)

    @Query("Select * from movie where imdbId = :id") fun getMovie(id: String): Maybe<Movie>
}
