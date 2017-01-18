package com.gbozza.android.popularmovies;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Adapter to manage the RecyclerView in the Main Activity
 */
class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesAdapterViewHolder> {

    private List<Movie> mMovieList;

    private final MoviesAdapterOnClickHandler mClickHandler;

    interface MoviesAdapterOnClickHandler {
        void onClick(String clickedItem);
    }

    MoviesAdapter(MoviesAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    class MoviesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
        int layoutIdForListItem = R.layout.popular_movies_card;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MoviesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesAdapterViewHolder moviesAdapterViewHolder, int position) {
        Movie movie = mMovieList.get(position);
        moviesAdapterViewHolder.mMovieTitleTextView.setText(movie.getOriginalTitle());

        Picasso.with(moviesAdapterViewHolder.mContext)
                .load(movie.buildPosterPath())
                .into(moviesAdapterViewHolder.mMoviePosterImageView);
    }

    @Override
    public int getItemCount() {
        if (null == mMovieList) return 0;
        return mMovieList.size();
    }

    void setMoviesData(List<Movie> movieList) {
        mMovieList = movieList;
        notifyDataSetChanged();
    }
}
