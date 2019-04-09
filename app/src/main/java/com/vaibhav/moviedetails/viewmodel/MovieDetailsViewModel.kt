package com.vaibhav.moviedetails.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.vaibhav.moviedetails.data.Movie
import com.vaibhav.moviedetails.repository.MovieRepository
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * @author Vaibhav Bhandula on 10/04/19.
 */
class MovieDetailsViewModel : ViewModel() {

    val movie = MutableLiveData<Movie>()
    private var movieRepository: MovieRepository? = null

    init {
        movieRepository = MovieRepository()
    }

    fun getMovieDetails(movieId: String) {
        movieRepository?.getMovieDetails(movieId, object : Observer<Movie> {
            override fun onComplete() {
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: Movie) {
                checkForBookMark(movieId, t)
            }

            override fun onError(e: Throwable) {
            }

        })
    }

    fun bookMarkMovie() {
        movieRepository?.bookMarkMovie(movie.value!!)
    }

    fun removeBookMark() {
        movieRepository?.removeBookMark(movie.value!!, null)
    }

    private fun checkForBookMark(movieId: String, movie: Movie) {
        movieRepository?.isMovieBookMarked(movieId, object : Observer<Boolean> {
            override fun onComplete() {
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: Boolean) {
                movie.bookmarked = t
                this@MovieDetailsViewModel.movie.value = movie
            }

            override fun onError(e: Throwable) {
                this@MovieDetailsViewModel.movie.value = movie
            }

        })
    }
}
