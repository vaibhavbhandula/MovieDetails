package com.vaibhav.moviedetails.contract;

import com.vaibhav.moviedetails.data.Movie;

import java.util.ArrayList;

/**
 * @author Vaibhav Bhandula on 09/10/18.
 */
public interface MovieListContract {

    void showSearchMovies(ArrayList<Movie> movies);

    void showBookMarkedMovies(ArrayList<Movie> movies);

    void showError(String error);
}
