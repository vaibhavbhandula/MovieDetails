package com.vaibhav.moviedetails.view.listener

import com.vaibhav.moviedetails.data.Movie

/**
 * @author Vaibhav Bhandula on 09/04/19.
 */
interface MovieListener {
    fun onLikeMovie(movie: Movie)
    fun onUnLikeMovie(movie: Movie)
}
