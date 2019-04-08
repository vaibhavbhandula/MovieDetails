package com.vaibhav.moviedetails.application

import android.arch.persistence.room.Room
import android.content.Context
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import com.vaibhav.moviedetails.commons.LogUtils
import com.vaibhav.moviedetails.db.MovieDatabase

/**
 * @author Vaibhav Bhandula on 08/04/19.
 */
class MovieDetailsApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        appDatabase = Room.databaseBuilder(applicationContext,
                MovieDatabase::class.java, "movieDatabase.db").build()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        try {
            MultiDex.install(this)
        } catch (e: Exception) {
            LogUtils.printStackTrace(e)
        }
    }

    companion object {
        private lateinit var appDatabase: MovieDatabase

        fun getMovieDao() = appDatabase.movieDao()
    }

}
