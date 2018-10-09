package com.vaibhav.moviedetails.listeners;

import com.vaibhav.moviedetails.data.Movie;

/**
 * @author Vaibhav Bhandula on 09/10/18.
 */
public interface MovieListener {

    void onLikeMovie(Movie movie);

    void onUnLikeMovie(Movie movie);
}
