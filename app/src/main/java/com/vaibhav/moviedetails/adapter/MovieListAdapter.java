package com.vaibhav.moviedetails.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vaibhav.moviedetails.R;
import com.vaibhav.moviedetails.data.Movie;
import com.vaibhav.moviedetails.listeners.MovieListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Vaibhav Bhandula on 09/10/18.
 */
public class MovieListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Movie> movies = new ArrayList<>();
    private ViewGroup parent;

    private MovieListener movieListener;

    public MovieListAdapter(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    @NonNull @Override public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.parent = viewGroup;
        return new MovieViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.movies_list_layout, viewGroup, false));
    }

    @Override public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((MovieViewHolder) viewHolder).bind(i);
    }

    @Override public int getItemCount() {
        return movies == null ? 0 : movies.size();
    }

    public void setMovieListener(MovieListener movieListener) {
        this.movieListener = movieListener;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_poster) ImageView ivPoster;
        @BindView(R.id.tv_name) TextView tvName;
        @BindView(R.id.tv_year) TextView tvYear;
        @BindView(R.id.iv_add) ImageView ivAdd;

        private Movie movie;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(int pos) {
            movie = movies.get(pos);
            tvName.setText(movie.getTitle());
            tvYear.setText(movie.getYear());
            Glide.with(parent)
                    .load(movie.getPoster())
                    .into(ivPoster);
            if (movie.isBookMarked()) {
                ivAdd.setVisibility(View.VISIBLE);
            } else {
                ivAdd.setVisibility(View.GONE);
            }
        }

        @OnClick(R.id.movie_layout) void movieClicked() {
            if (movieListener == null) {
                return;
            }
            if (movie.isBookMarked()) {
                movie.setBookMarked(false);
                movieListener.onUnLikeMovie(movie);
                ivAdd.setVisibility(View.GONE);
            } else {
                movie.setBookMarked(true);
                movieListener.onLikeMovie(movie);
                ivAdd.setVisibility(View.VISIBLE);
            }
        }
    }
}
