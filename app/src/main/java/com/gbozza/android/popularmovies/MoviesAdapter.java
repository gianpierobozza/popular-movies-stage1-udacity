package com.gbozza.android.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by gianpiero.bozza on 17-Jan-17.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesAdapterViewHolder> {

    // TODO change according to the TheMovieDB return object
    private Object[] mPopularMovies;

    private final MoviesAdapterOnClickHandler mClickHandler;

    public interface MoviesAdapterOnClickHandler {
        void onClick(String clickedItem);
    }

    public MoviesAdapter(MoviesAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class MoviesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView mMoviePosterView;

        public MoviesAdapterViewHolder(View view) {
            super(view);
            mMoviePosterView = (ImageView) view.findViewById(R.id.iv_movie_poster);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            // TODO get the id of the clicked movie poster: String movieId = mData[adapterPosition];
            // mClickHandler.onClick(movieId);
        }
    }

    @Override
    public MoviesAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movies_grid_layout;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MoviesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesAdapterViewHolder moviesAdapterViewHolder, int position) {
        // TODO get the poster at the specified position and assign it to the ImageView
        // pseudo code: Image poster = mPopularMovies[position];
        //moviesAdapterViewHolder.mMoviePosterView.setImage(poster);
    }

    @Override
    public int getItemCount() {
        // TODO change according to the TheMovieDB return object
        if (null == mPopularMovies) return 0;
        return mPopularMovies.length;
    }

    public void setMoviesData(Object[] moviesData) {
        mPopularMovies = moviesData;
        notifyDataSetChanged();
    }
}
