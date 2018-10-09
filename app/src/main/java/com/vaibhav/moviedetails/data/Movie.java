package com.vaibhav.moviedetails.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
import com.vaibhav.moviedetails.commons.StringUtils;

/**
 * @author Vaibhav Bhandula on 09/10/18.
 */
@Entity
public class Movie {

    @PrimaryKey @SerializedName("imdbID") private String imdbId = "";
    @SerializedName("Title") private String title = "";
    @SerializedName("Year") private String year = "";
    @SerializedName("Genre") private String genre = "";
    @SerializedName("Director") private String director = "";
    @SerializedName("imdbRating") private String imdbRating = "";
    @SerializedName("Poster") private String poster = "";
    @SerializedName("Actors") private String actors = "";

    public String getImdbId() {
        return StringUtils.getNonNullString(imdbId);
    }

    public String getTitle() {
        return StringUtils.getNonNullString(title);
    }

    public String getYear() {
        return StringUtils.getNonNullString(year);
    }

    public String getGenre() {
        return StringUtils.getNonNullString(genre);
    }

    public String getDirector() {
        return StringUtils.getNonNullString(director);
    }

    public String getImdbRating() {
        return StringUtils.getNonNullString(imdbRating);
    }

    public String getPoster() {
        return StringUtils.getNonNullString(poster);
    }

    public String getActors() {
        return StringUtils.getNonNullString(actors);
    }
}
