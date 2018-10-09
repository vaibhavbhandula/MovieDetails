package com.vaibhav.moviedetails.viewmodels;

import android.arch.lifecycle.ViewModel;

import com.vaibhav.moviedetails.contract.MovieListContract;
import com.vaibhav.moviedetails.data.MovieResponse;
import com.vaibhav.moviedetails.network.ApiClient;
import com.vaibhav.moviedetails.network.ApiInterface;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Vaibhav Bhandula on 09/10/18.
 */
public class MovieListViewModel extends ViewModel {

    private MovieListContract movieListContract;

    private String query = "";
    private int page = 1;

    public MovieListViewModel(MovieListContract movieListContract) {
        this.movieListContract = movieListContract;
    }

    public void initViewModel() {
        setQuery("friends");
    }

    public void setQuery(String query) {
        if (this.query.equalsIgnoreCase(query)) {
            return;
        }
        this.query = query;
        getMovies();
    }

    private void getMovies() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Observable<MovieResponse> call = apiInterface.getMovieList(page, query);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieResponse>() {
                    @Override public void onSubscribe(Disposable d) {

                    }

                    @Override public void onNext(MovieResponse movieResponse) {
                        if (movieListContract != null && movieResponse != null) {
                            movieListContract.showSearchMovies(movieResponse.getMovies());
                        }
                    }

                    @Override public void onError(Throwable e) {
                        if (movieListContract != null) {
                            movieListContract.showError(e.getMessage());
                        }
                    }

                    @Override public void onComplete() {

                    }
                });
    }
}
