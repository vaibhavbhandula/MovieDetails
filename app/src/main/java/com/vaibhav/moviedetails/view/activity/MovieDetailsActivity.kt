package com.vaibhav.moviedetails.view.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.vaibhav.moviedetails.R
import com.vaibhav.moviedetails.data.Movie
import com.vaibhav.moviedetails.databinding.ActivityMovieDetailsBinding
import com.vaibhav.moviedetails.viewmodel.MovieDetailsViewModel
import kotlinx.android.synthetic.main.activity_movie_details.*

/**
 * @author Vaibhav Bhandula on 09/04/19.
 */
class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var movieDetailsViewModel: MovieDetailsViewModel
    private lateinit var binding: ActivityMovieDetailsBinding

    private var movieId: String = ""
    private var isBookMarked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details)
        readBundle()
        initWidgets()
    }

    private fun readBundle() {
        val bundle = intent.extras
        if (bundle != null && bundle.containsKey("movie_id")) {
            movieId = bundle.getString("movie_id")!!
        }
    }

    private fun initWidgets() {
        movieDetailsViewModel = ViewModelProviders.of(this).get(MovieDetailsViewModel::class.java)
        movieDetailsViewModel.movie.observe(this, Observer<Movie> { t -> updateMovieData(t) })
        movieDetailsViewModel.getMovieDetails(movieId)
        iv_bookmark?.setOnClickListener { handleBookMark() }
    }

    private fun updateMovieData(movie: Movie?) {
        binding.movie = movie
        isBookMarked = movie?.bookmarked ?: false
        handleBookMarkUI()
    }

    private fun handleBookMark() {
        if (isBookMarked) {
            isBookMarked = false
            movieDetailsViewModel.removeBookMark()
        } else {
            isBookMarked = true
            movieDetailsViewModel.bookMarkMovie()
        }
        handleBookMarkUI()
    }

    private fun handleBookMarkUI() {
        if (isBookMarked) {
            iv_bookmark?.setImageResource(R.drawable.bookmarked)
        } else {
            iv_bookmark?.setImageResource(R.drawable.un_bookmarked)
        }
    }
}
