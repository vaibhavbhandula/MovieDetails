package com.vaibhav.moviedetails.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.vaibhav.moviedetails.dao.MovieDao
import com.vaibhav.moviedetails.data.Movie

/**
 * @author Vaibhav Bhandula on 08/04/19.
 */
@Database(entities = [Movie::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
