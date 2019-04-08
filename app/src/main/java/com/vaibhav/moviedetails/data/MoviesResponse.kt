package com.vaibhav.moviedetails.data

import com.google.gson.annotations.SerializedName

/**
 * @author Vaibhav Bhandula on 08/04/19.
 */
data class MoviesResponse(
        @SerializedName("Search") val movies: ArrayList<Movie> = ArrayList()
)
