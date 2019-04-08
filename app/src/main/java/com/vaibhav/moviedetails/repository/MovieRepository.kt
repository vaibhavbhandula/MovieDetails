package com.vaibhav.moviedetails.repository

import com.vaibhav.moviedetails.application.MovieDetailsApplication
import com.vaibhav.moviedetails.commons.LogUtils
import com.vaibhav.moviedetails.data.Movie
import com.vaibhav.moviedetails.data.MoviesResponse
import com.vaibhav.moviedetails.network.ApiClient
import com.vaibhav.moviedetails.network.ApiInterface
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * @author Vaibhav Bhandula on 08/04/19.
 */
class MovieRepository {

    private var apiInterface: ApiInterface = ApiClient.getClient().create(ApiInterface::class.java)

    fun getMoviesList(mPage: Int, mQuery: String, observer: Observer<ArrayList<Movie>>) {
        val call = apiInterface.getMovieList(mPage, mQuery)
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<MoviesResponse> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: MoviesResponse) {
                        observer.onNext(t.movies)
                    }

                    override fun onError(e: Throwable) {
                        observer.onError(e)
                        LogUtils.printStackTrace(e)
                    }

                })
    }

    fun getMovieDetails(id: String, observer: Observer<Movie>) {
        val call = apiInterface.getMovieDetails(id)
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Movie> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: Movie) {
                        observer.onNext(t)
                    }

                    override fun onError(e: Throwable) {
                        observer.onError(e)
                        LogUtils.printStackTrace(e)
                    }

                })
    }

    fun bookMarkMovie(movie: Movie) {
        Observable.just(movie)
                .subscribeOn(Schedulers.io())
                .subscribe(object : Observer<Movie> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(movie: Movie) {
                        MovieDetailsApplication.getMovieDao().insert(movie)
                    }

                    override fun onError(e: Throwable) {
                        LogUtils.printLog("movie_bookmark", "error")
                    }

                    override fun onComplete() {
                        LogUtils.printLog("movie_bookmark", "added")
                    }
                })
    }

    fun removeBookMark(movie: Movie) {
        Observable.just(movie)
                .subscribeOn(Schedulers.io())
                .subscribe(object : Observer<Movie> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(movie: Movie) {
                        MovieDetailsApplication.getMovieDao().delete(movie)
                    }

                    override fun onError(e: Throwable) {
                        LogUtils.printLog("movie_bookmark", "error")
                    }

                    override fun onComplete() {
                        LogUtils.printLog("movie_bookmark", "removed")
                    }
                })
    }

    fun getBookMarkedMovies(observer: Observer<ArrayList<Movie>>) {
        val disposable = MovieDetailsApplication.getMovieDao()
                .getAllMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { movies ->
                    observer.onNext(ArrayList(movies))
                }
    }
}
