package com.vaibhav.moviedetails.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * @author Vaibhav Bhandula on 08/04/19.
 */
@Entity
data class Movie(
        @PrimaryKey @SerializedName("imdbID") val imdbId: String,
        @SerializedName("Title") val title: String?,
        @SerializedName("Year") val year: String?,
        @SerializedName("Genre") val genre: String?,
        @SerializedName("Director") val director: String?,
        @SerializedName("imdbRating") val imdbRating: String?,
        @SerializedName("Poster") val poster: String?,
        @SerializedName("Actors") val actors: String?
) {

    var bookmarked: Boolean = false
}
