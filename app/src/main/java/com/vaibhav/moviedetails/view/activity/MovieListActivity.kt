package com.vaibhav.moviedetails.view.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.nfc.tech.MifareUltralight
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.inputmethod.InputMethodManager
import com.vaibhav.moviedetails.R
import com.vaibhav.moviedetails.data.Movie
import com.vaibhav.moviedetails.view.adapter.MoviesAdapter
import com.vaibhav.moviedetails.view.listener.MovieListener
import com.vaibhav.moviedetails.viewmodel.MovieListViewModel
import kotlinx.android.synthetic.main.activity_movie_list.*
import java.util.*

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
        setUpSearch()
    }

    private fun setUpMoviesList() {
        moviesAdapter = MoviesAdapter(MoviesAdapter.Type.SEARCH)
        moviesAdapter?.setMovieListener(this)
        movies_view?.layoutManager = LinearLayoutManager(this)
        movies_view?.adapter = moviesAdapter
        movies_view?.addOnScrollListener(recyclerViewOnScrollListener)
    }

    private fun setUpBookMarkList() {
        bookmarksAdapter = MoviesAdapter(MoviesAdapter.Type.BOOKMARKS)
        bookmarksAdapter?.setMovieListener(this)
        bookmark_view?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false)
        bookmark_view?.adapter = bookmarksAdapter
    }

    private fun setUpSearch() {
        search_view?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            var timer: Timer = Timer()
            val DELAY = 500
            override fun onQueryTextSubmit(p0: String?): Boolean {
                sendQuery(p0)
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                sendQuery(p0)
                return false
            }

            private fun sendQuery(s: String?) {
                timer.cancel()
                timer = Timer()
                timer.schedule(object : TimerTask() {
                    override fun run() {
                        runOnUiThread {
                            if (!s.isNullOrEmpty()) {
                                movieListViewModel?.setQuery(s)
                            }
                        }
                        cancel()
                    }
                }, DELAY.toLong())
            }

        })
    }

    private fun hideSoftKeyBoard() {
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    override fun onLikeMovie(movie: Movie) {
        movieListViewModel?.addBookMark(movie)
    }

    override fun onUnLikeMovie(movie: Movie) {
        movieListViewModel?.deleteBookMark(movie)
    }

    override fun openMovie(movie: Movie) {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        val bundle = Bundle().apply {
            putString("movie_id", movie.imdbId)
        }
        intent.putExtras(bundle)
        startActivity(intent)
    }

    private val recyclerViewOnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val visibleItemCount = recyclerView.layoutManager!!.childCount
            val totalItemCount = recyclerView.layoutManager!!.itemCount
            val firstVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager)
                    .findFirstVisibleItemPosition()

            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                    && firstVisibleItemPosition >= 0
                    && totalItemCount >= MifareUltralight.PAGE_SIZE) {
                movieListViewModel?.loadNextPage()
            }
            if (dy > 0) {
                hideSoftKeyBoard()
            }
        }
    }
}
