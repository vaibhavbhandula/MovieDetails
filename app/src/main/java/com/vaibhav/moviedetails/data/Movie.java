package com.vaibhav.moviedetails.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.vaibhav.moviedetails.commons.StringUtils;

/**
 * @author Vaibhav Bhandula on 09/10/18.
 */
@Entity
public class Movie {

    @PrimaryKey @SerializedName("imdbID") @NonNull private String imdbId = "";
    @SerializedName("Title") private String title = "";
    @SerializedName("Year") private String year = "";
    @SerializedName("Genre") private String genre = "";
    @SerializedName("Director") private String director = "";
    @SerializedName("imdbRating") private String imdbRating = "";
    @SerializedName("Poster") private String poster = "";
    @SerializedName("Actors") private String actors = "";

    private boolean isBookMarked = false;

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

    public void setImdbId(@NonNull String imdbId) {
        this.imdbId = imdbId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public boolean isBookMarked() {
        return isBookMarked;
    }

    public void setBookMarked(boolean bookMarked) {
        isBookMarked = bookMarked;
    }
}
