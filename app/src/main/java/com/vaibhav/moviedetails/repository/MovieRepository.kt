package com.vaibhav.moviedetails.repository

import android.annotation.SuppressLint
import com.vaibhav.moviedetails.application.MovieDetailsApplication
import com.vaibhav.moviedetails.commons.LogUtils
import com.vaibhav.moviedetails.data.Movie
import com.vaibhav.moviedetails.network.ApiClient
import com.vaibhav.moviedetails.network.ApiInterface
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author Vaibhav Bhandula on 08/04/19.
 */
@SuppressLint("CheckResult")
class MovieRepository {

    private var apiInterface: ApiInterface = ApiClient.getClient().create(ApiInterface::class.java)

    fun getMoviesList(mPage: Int, mQuery: String, observer: Observer<ArrayList<Movie>>?) {
        val call = apiInterface.getMovieList(mPage, mQuery)
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    observer?.onNext(it.movies)
                }, {
                    observer?.onError(it)
                    LogUtils.printStackTrace(it)
                })
    }

    fun getMovieDetails(id: String, observer: Observer<Movie>?) {
        val call = apiInterface.getMovieDetails(id)
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    observer?.onNext(it)
                }, {
                    observer?.onError(it)
                    LogUtils.printStackTrace(it)
                })
    }

    fun bookMarkMovie(movie: Movie) {
        Observable.just(movie)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    MovieDetailsApplication.getMovieDao().insert(movie)
                }, {
                    LogUtils.printLog("movie_bookmark", "error")
                }, {
                    LogUtils.printLog("movie_bookmark", "added")
                })
    }

    fun removeBookMark(movie: Movie, observer: Observer<Boolean>?) {
        Observable.just(movie)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    MovieDetailsApplication.getMovieDao().delete(movie)
                }, {
                    LogUtils.printLog("movie_bookmark", "error")
                    observer?.onError(it)
                }, {
                    LogUtils.printLog("movie_bookmark", "removed")
                    observer?.onNext(true)
                })
    }

    fun getBookMarkedMovies(observer: Observer<ArrayList<Movie>>?) {
        val disposable = MovieDetailsApplication.getMovieDao()
                .getAllMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { movies ->
                    observer?.onNext(ArrayList(movies))
                }
    }

    fun isMovieBookMarked(id: String, observer: Observer<Boolean>?) {
        val disposable = MovieDetailsApplication.getMovieDao()
                .getMovie(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    observer?.onNext(true)
                }, {
                    LogUtils.printStackTrace(it)
                    observer?.onError(it)
                }, {
                    observer?.onNext(false)
                })
    }
}
