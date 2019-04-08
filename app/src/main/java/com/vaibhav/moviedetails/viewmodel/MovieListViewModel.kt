package com.vaibhav.moviedetails.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.os.Handler
import android.os.Looper
import com.vaibhav.moviedetails.data.Movie
import com.vaibhav.moviedetails.repository.MovieRepository
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * @author Vaibhav Bhandula on 08/04/19.
 */
class MovieListViewModel : ViewModel() {

    var moviesList = MutableLiveData<ArrayList<Movie>>()
    var bookMarkedList = MutableLiveData<ArrayList<Movie>>()
    private var moviesRepository = MovieRepository()

    private var query = ""
    private var pageNo = 1

    init {
        setQuery("friends")
        getBookMarkedMovies()
    }

    fun setQuery(query: String) {
        if (this.query.equals(query, ignoreCase = true)) {
            return
        }
        this.query = query
        getMovies()
    }

    fun addBookMark(movie: Movie) {
        moviesRepository.bookMarkMovie(movie)
    }

    fun deleteBookMark(movie: Movie) {
        moviesRepository.removeBookMark(movie, object : Observer<Boolean> {
            override fun onComplete() {
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: Boolean) {
                if (t) {
                    updateBookMarksInSearch()
                }
            }

            override fun onError(e: Throwable) {
            }

        })
    }

    private fun getMovies() {
        moviesRepository.getMoviesList(pageNo, query, object : Observer<ArrayList<Movie>> {
            override fun onSubscribe(d: Disposable) {
            }

            override fun onComplete() {
            }

            override fun onNext(t: ArrayList<Movie>) {
                checkForBookMarks(t)
            }

            override fun onError(e: Throwable) {
            }

        })
    }

    private fun getBookMarkedMovies() {
        moviesRepository.getBookMarkedMovies(object : Observer<ArrayList<Movie>> {
            override fun onSubscribe(d: Disposable) {
            }

            override fun onComplete() {
            }

            override fun onNext(t: ArrayList<Movie>) {
                bookMarkedList.value = t
            }

            override fun onError(e: Throwable) {
            }

        })
    }

    private fun checkForBookMarks(movies: ArrayList<Movie>) {
        movies.forEach {
            val item = bookMarkedList.value?.find { bookmarked -> bookmarked.imdbId == it.imdbId }
            it.bookmarked = (item != null)
        }
        moviesList.value = movies
    }

    private fun updateBookMarksInSearch() {
        Handler(Looper.getMainLooper()).postDelayed({
            val movies = moviesList.value ?: ArrayList()
            movies.forEach {
                val item = bookMarkedList.value?.find { bookmarked -> bookmarked.imdbId == it.imdbId }
                if (item != null) {
                    it.bookmarked = item.bookmarked
                } else {
                    it.bookmarked = false
                }
            }
            moviesList.postValue(movies)
        }, 200)
    }
}
