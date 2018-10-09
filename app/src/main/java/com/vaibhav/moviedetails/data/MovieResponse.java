package com.vaibhav.moviedetails.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * @author Vaibhav Bhandula on 09/10/18.
 */
public class MovieResponse {

    @SerializedName("Search") private ArrayList<Movie> movies = new ArrayList<>();

    public ArrayList<Movie> getMovies() {
        if (movies == null) {
            return new ArrayList<>();
        }
        return movies;
    }
}
