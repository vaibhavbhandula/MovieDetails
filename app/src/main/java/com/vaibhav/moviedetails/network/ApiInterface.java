package com.vaibhav.moviedetails.network;

import com.vaibhav.moviedetails.data.Movie;
import com.vaibhav.moviedetails.data.MovieResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author Vaibhav Bhandula on 09/10/18.
 */
public interface ApiInterface {

    @GET("/")
    Observable<MovieResponse> getMovieList(@Query("page") int mPage, @Query("s") String mQuery);

    @GET("/")
    Observable<Movie> getMovieDetails(@Query("i") String imdbId);
}
