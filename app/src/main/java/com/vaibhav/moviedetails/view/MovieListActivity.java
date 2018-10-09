package com.vaibhav.moviedetails.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import com.vaibhav.moviedetails.R;
import com.vaibhav.moviedetails.adapter.MovieListAdapter;
import com.vaibhav.moviedetails.contract.MovieListContract;
import com.vaibhav.moviedetails.data.Movie;
import com.vaibhav.moviedetails.listeners.MovieListener;
import com.vaibhav.moviedetails.viewmodels.MovieListViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Vaibhav Bhandula on 09/10/18.
 */
public class MovieListActivity extends AppCompatActivity implements MovieListContract, MovieListener {

    @BindView(R.id.search_view) SearchView searchView;
    @BindView(R.id.bookmark_view) RecyclerView bookmarkView;
    @BindView(R.id.movies_view) RecyclerView moviesView;

    private MovieListViewModel movieListViewModel;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        ButterKnife.bind(this);
        initWidgets();
    }

    private void initWidgets() {
        searchView.setQuery("friends", false);
        movieListViewModel = new MovieListViewModel(this, this);
        movieListViewModel.initViewModel();
    }

    @Override public void showSearchMovies(ArrayList<Movie> movies) {
        MovieListAdapter movieListAdapter = new MovieListAdapter(movies);
        movieListAdapter.setMovieListener(this);
        moviesView.setLayoutManager(new LinearLayoutManager(this));
        moviesView.setAdapter(movieListAdapter);
    }

    @Override public void showBookMarkedMovies(ArrayList<Movie> movies) {
        MovieListAdapter movieListAdapter = new MovieListAdapter(movies);
        movieListAdapter.setMovieListener(this);
        bookmarkView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false));
        bookmarkView.setAdapter(movieListAdapter);
    }

    @Override public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override public void onLikeMovie(Movie movie) {
        if (movieListViewModel != null) {
            movieListViewModel.bookMarkMovie(movie);
        }
    }

    @Override public void onUnLikeMovie(Movie movie) {
        if (movieListViewModel != null) {
            movieListViewModel.removeBookMark(movie);
        }
    }
}
