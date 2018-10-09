package com.vaibhav.moviedetails.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.vaibhav.moviedetails.commons.LogUtils;
import com.vaibhav.moviedetails.contract.MovieListContract;
import com.vaibhav.moviedetails.data.Movie;
import com.vaibhav.moviedetails.data.MovieResponse;
import com.vaibhav.moviedetails.db.MoviesDatabase;
import com.vaibhav.moviedetails.network.ApiClient;
import com.vaibhav.moviedetails.network.ApiInterface;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Vaibhav Bhandula on 09/10/18.
 */
public class MovieListViewModel extends ViewModel {

    private static final String TAG = MovieListViewModel.class.getSimpleName();

    private WeakReference<Context> weakReference;
    private MovieListContract movieListContract;

    private String query = "";
    private int page = 1;

    public MovieListViewModel(Context context, MovieListContract movieListContract) {
        weakReference = new WeakReference<>(context);
        this.movieListContract = movieListContract;
    }

    public void initViewModel() {
        setQuery("friends");
        getBookMarkedMovies();
    }

    public void setQuery(String query) {
        if (this.query.equalsIgnoreCase(query)) {
            return;
        }
        this.query = query;
        getMovies();
    }

    public void bookMarkMovie(Movie movie) {
        Observable.just(movie)
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Movie>() {
                    @Override public void onSubscribe(Disposable d) {

                    }

                    @Override public void onNext(Movie movie) {
                        MoviesDatabase.getInstance(getContext()).getEventDao().insert(movie);
                    }

                    @Override public void onError(Throwable e) {
                        LogUtils.printLog(TAG, "error");
                    }

                    @Override public void onComplete() {
                        LogUtils.printLog(TAG, "added");
                    }
                });
    }

    public void removeBookMark(Movie movie) {
        Observable.just(movie)
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Movie>() {
                    @Override public void onSubscribe(Disposable d) {

                    }

                    @Override public void onNext(Movie movie) {
                        MoviesDatabase.getInstance(getContext()).getEventDao().delete(movie);
                    }

                    @Override public void onError(Throwable e) {
                        LogUtils.printLog(TAG, "error");
                    }

                    @Override public void onComplete() {
                        LogUtils.printLog(TAG, "removed");
                    }
                });
    }

    private void getBookMarkedMovies() {
        Disposable disposable = MoviesDatabase.getInstance(getContext())
                .getEventDao()
                .getAllMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movies -> {
                    if (movieListContract != null) {
                        movieListContract.showBookMarkedMovies(new ArrayList<>(movies));
                    }
                });
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

    private Context getContext() {
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }
}
