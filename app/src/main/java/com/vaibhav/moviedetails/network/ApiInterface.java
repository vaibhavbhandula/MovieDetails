package com.vaibhav.moviedetails.network;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author Vaibhav Bhandula on 09/10/18.
 */
public interface ApiInterface {

    @GET("/")
    Observable<Void> getMovieList(@Query("page") int mPage, @Query("s") String mQuery);

    @GET("/")
    Observable<Void> getMovieDetails(@Query("i") String imdbId);
}
