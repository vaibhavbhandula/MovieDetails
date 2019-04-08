package com.vaibhav.moviedetails.view.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.vaibhav.moviedetails.R
import com.vaibhav.moviedetails.data.Movie
import com.vaibhav.moviedetails.view.adapter.MoviesAdapter
import com.vaibhav.moviedetails.view.listener.MovieListener
import com.vaibhav.moviedetails.viewmodel.MovieListViewModel
import kotlinx.android.synthetic.main.activity_movie_list.*

/**
 * @author Vaibhav Bhandula on 08/04/19.
 */
class MovieListActivity : AppCompatActivity(), MovieListener {

    private var movieListViewModel: MovieListViewModel? = null
    private var moviesAdapter: MoviesAdapter? = null
    private var bookmarksAdapter: MoviesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)
        initWidgets()
    }

    private fun initWidgets() {
        movieListViewModel = ViewModelProviders.of(this).get(MovieListViewModel::class.java)
        movieListViewModel?.moviesList?.observe(this, Observer<ArrayList<Movie>> {
            moviesAdapter?.updateList(it!!)
        })
        movieListViewModel?.bookMarkedList?.observe(this, Observer<ArrayList<Movie>> {
            bookmarksAdapter?.updateList(it!!)
        })
        setUpMoviesList()
        setUpBookMarkList()
    }

    private fun setUpMoviesList() {
        moviesAdapter = MoviesAdapter()
        moviesAdapter?.setMovieListener(this)
        movies_view?.layoutManager = LinearLayoutManager(this)
        movies_view?.adapter = moviesAdapter
    }

    private fun setUpBookMarkList() {
        bookmarksAdapter = MoviesAdapter()
        bookmarksAdapter?.setMovieListener(this)
        bookmark_view?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false)
        bookmark_view?.adapter = bookmarksAdapter
    }

    override fun onLikeMovie(movie: Movie) {
        movieListViewModel?.addBookMark(movie)
    }

    override fun onUnLikeMovie(movie: Movie) {
        movieListViewModel?.deleteBookMark(movie)
    }
}
