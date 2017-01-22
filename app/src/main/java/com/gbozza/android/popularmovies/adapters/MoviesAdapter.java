package com.gbozza.android.popularmovies.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gbozza.android.popularmovies.MovieDetailActivity;
import com.gbozza.android.popularmovies.R;
import com.gbozza.android.popularmovies.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Adapter to manage the RecyclerView in the Main Activity
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesAdapterViewHolder> {

    private List<Movie> mMovieList;

    private static final String INTENT_MOVIE_KEY = "movieObject";

    class MoviesAdapterViewHolder extends RecyclerView.ViewHolder {
        final CardView mPopularMovieCardView;
        final ImageView mMoviePosterImageView;
        final TextView mMovieTitleTextView;
        Context mContext;

        MoviesAdapterViewHolder(View view) {
            super(view);
            mPopularMovieCardView = (CardView) view.findViewById(R.id.cv_popular_movie);
            mMoviePosterImageView = (ImageView) view.findViewById(R.id.iv_movie_poster);
            mMovieTitleTextView = (TextView) view.findViewById(R.id.tv_movie_title);
            mContext = view.getContext();
        }
    }

    @Override
    public MoviesAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.popular_movies_card;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MoviesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesAdapterViewHolder moviesAdapterViewHolder, int position) {
        Movie movie = mMovieList.get(position);
        Picasso.with(moviesAdapterViewHolder.mContext)
                .load(movie.buildPosterPath(moviesAdapterViewHolder.mContext))
                .into(moviesAdapterViewHolder.mMoviePosterImageView);
        moviesAdapterViewHolder.mMovieTitleTextView.setText(movie.getOriginalTitle());

        moviesAdapterViewHolder.mPopularMovieCardView.setTag(R.id.card_view_item, position);

        moviesAdapterViewHolder.mPopularMovieCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Class destinationClass = MovieDetailActivity.class;
                Intent movieDetailIntent = new Intent(view.getContext(), destinationClass);
                int position = (int) view.getTag(R.id.card_view_item);
                movieDetailIntent.putExtra(INTENT_MOVIE_KEY, mMovieList.get(position));
                view.getContext().startActivity(movieDetailIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (null == mMovieList) return 0;
        return mMovieList.size();
    }

    public void clear() {
        if (mMovieList != null) {
            mMovieList.clear();
            notifyDataSetChanged();
        }
    }

    public void setMoviesData(List<Movie> movieList) {
        if (null == mMovieList) {
            mMovieList = movieList;
        } else {
            mMovieList.addAll(movieList);
        }
        notifyDataSetChanged();
    }

    public List<Movie> getMoviesData() {
        return mMovieList;
    }
}
