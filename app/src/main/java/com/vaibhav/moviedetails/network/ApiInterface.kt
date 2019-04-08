package com.vaibhav.moviedetails.network

import com.vaibhav.moviedetails.data.Movie
import com.vaibhav.moviedetails.data.MoviesResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * @author Vaibhav Bhandula on 08/04/19.
 */
interface ApiInterface {

    @GET("/")
    fun getMovieList(@Query("page") mPage: Int, @Query("s") mQuery: String): Observable<MoviesResponse>

    @GET("/")
    fun getMovieDetails(@Query("i") imdbId: String): Observable<Movie>
}
