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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Vaibhav Bhandula on 09/10/18.
 */
public class MovieListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Movie> movies = new ArrayList<>();
    private ViewGroup parent;

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

    class MovieViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_poster) ImageView ivPoster;
        @BindView(R.id.tv_name) TextView tvName;
        @BindView(R.id.tv_year) TextView tvYear;

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
        }
    }
}
