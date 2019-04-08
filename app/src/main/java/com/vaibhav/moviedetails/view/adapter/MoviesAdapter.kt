package com.vaibhav.moviedetails.view.adapter

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.vaibhav.moviedetails.R
import com.vaibhav.moviedetails.data.Movie
import com.vaibhav.moviedetails.view.listener.MovieListener
import kotlinx.android.synthetic.main.movies_list_layout.view.*

/**
 * @author Vaibhav Bhandula on 08/04/19.
 */
class MoviesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var moviesList = ArrayList<Movie>()
    private var movieListener: MovieListener? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return MovieViewHolder(LayoutInflater.from(p0.context)
                .inflate(R.layout.movies_list_layout, p0, false))
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        (p0 as MovieViewHolder).bind(p1)
    }

    fun setMovieListener(movieListener: MovieListener?) {
        this.movieListener = movieListener
    }

    fun updateList(movies: ArrayList<Movie>) {
        val result = DiffUtil.calculateDiff(MovieDiffUtil(this.moviesList, movies))
        this.moviesList.clear()
        this.moviesList.addAll(movies)
        result.dispatchUpdatesTo(this)
    }

    private inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var movie: Movie? = null

        init {
            itemView.movie_layout?.setOnClickListener { bookmarkedClicked() }
        }

        fun bind(pos: Int) {
            movie = moviesList[pos]
            val movieUrl = movie?.poster ?: ""
            if (!movieUrl.isEmpty()) {
                Glide.with(itemView)
                        .load(movie?.poster)
                        .into(itemView.iv_poster)
            }
            itemView.tv_name?.text = movie?.title ?: ""
            itemView.tv_year?.text = movie?.year
            if (movie?.bookmarked == true) {
                itemView.iv_add?.visibility = View.VISIBLE
            } else {
                itemView.iv_add?.visibility = View.GONE
            }
        }

        private fun bookmarkedClicked() {
            if (movieListener == null || movie == null) {
                return
            }
            if (movie?.bookmarked == true) {
                movie?.bookmarked = false
                itemView.iv_add?.visibility = View.GONE
                movieListener?.onUnLikeMovie(movie!!)
            } else {
                movie?.bookmarked = true
                itemView.iv_add?.visibility = View.VISIBLE
                movieListener?.onLikeMovie(movie!!)
            }
        }
    }

    class MovieDiffUtil(private val mOldList: ArrayList<Movie>,
                        private val mNewList: ArrayList<Movie>) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val newItem = mNewList[newItemPosition]
            val oldItem = mOldList[oldItemPosition]
            return newItem.imdbId.equals(oldItem.imdbId, ignoreCase = true)
        }

        override fun getOldListSize() = mOldList.size

        override fun getNewListSize() = mNewList.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = false
    }
}
