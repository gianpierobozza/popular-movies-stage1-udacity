package com.gbozza.android.popularmovies.utilities;

import android.view.View;

import com.gbozza.android.popularmovies.adapters.MoviesAdapter;
import com.squareup.picasso.Callback;

/**
 * Extension of the Callback interface from Picasso
 */
public class MoviePosterCallback extends Callback.EmptyCallback {

    private MoviesAdapter.MoviesAdapterViewHolder mViewHolder;

    /**
     * Constructor
     *
     * @param viewHolder The ViewHolder of the class using this extension
     */
    public MoviePosterCallback(MoviesAdapter.MoviesAdapterViewHolder viewHolder) {
        this.mViewHolder = viewHolder;
    }

    @Override
    public void onSuccess() {
        mViewHolder.mMoviePosterProgressBar.setVisibility(View.GONE);
        mViewHolder.mMoviePosterErrorTextView.setVisibility(View.GONE);
    }

    @Override
    public void onError() {
        mViewHolder.mMoviePosterProgressBar.setVisibility(View.GONE);
        mViewHolder.mMoviePosterErrorTextView.setRotation(-20);
        mViewHolder.mMoviePosterErrorTextView.setVisibility(View.VISIBLE);
    }
}