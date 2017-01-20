package com.gbozza.android.popularmovies.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.gbozza.android.popularmovies.R;
import com.gbozza.android.popularmovies.adapters.MoviesAdapter;
import com.gbozza.android.popularmovies.models.Movie;
import com.gbozza.android.popularmovies.utilities.EndlessRecyclerViewScrollListener;
import com.gbozza.android.popularmovies.utilities.MovieDbJsonUtilities;
import com.gbozza.android.popularmovies.utilities.NetworkUtilities;

import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieListFragment extends Fragment {

    private MoviesAdapter mMoviesAdapter;
    private ProgressBar mLoadingIndicator;
    private EndlessRecyclerViewScrollListener mScrollListener;
    private SwipeRefreshLayout mSwipeContainer;
    private int mPage;
    private int mSorting;

    private static final int SORTING_POPULAR = 1;
    private static final int SORTING_RATED = 2;
    private static final String BUNDLE_MOVIES_KEY = "movieList";
    private static final String BUNDLE_PAGE_KEY = "currentPage";
    private static final String BUNDLE_SORTING_KEY = "currentSorting";

    private static final String TAG = MovieListFragment.class.getSimpleName();

    public MovieListFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mPage = savedInstanceState.getInt(BUNDLE_PAGE_KEY);
            mSorting = savedInstanceState.getInt(BUNDLE_SORTING_KEY);
        } else {
            mPage = 1;
            mSorting = 1;
        }

        View rootView = inflater.inflate(R.layout.movie_list_fragment, container, false);

        Context context = getContext();
        final int columns = getResources().getInteger(R.integer.grid_columns);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, columns, GridLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_posters);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setHasFixedSize(true);
        mMoviesAdapter = new MoviesAdapter();
        recyclerView.setAdapter(mMoviesAdapter);

        mLoadingIndicator = (ProgressBar) rootView.findViewById(R.id.pb_loading_indicator);
        mScrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager, mPage) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Log.d(TAG, "Loading page: " + String.valueOf(page));
                mPage = page;
                loadCards(mSorting);
            }
        };
        recyclerView.addOnScrollListener(mScrollListener);

        mSwipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.sr_swipe_container);
        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                clearCardView();
                loadCards(mSorting);
            }
        });
        mSwipeContainer.setColorSchemeResources(R.color.colorAccent);

        if (savedInstanceState != null) {
            ArrayList<Movie> movieList = savedInstanceState.getParcelableArrayList(BUNDLE_MOVIES_KEY);
            mMoviesAdapter.setMoviesData(movieList);
        }
        else {
            loadCards(mSorting);
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ArrayList<Movie> movieList = new ArrayList<>(mMoviesAdapter.getMoviesData());
        outState.putParcelableArrayList(BUNDLE_MOVIES_KEY, movieList);
        outState.putInt(BUNDLE_PAGE_KEY, mPage);
        outState.putInt(BUNDLE_SORTING_KEY, mSorting);
    }

    public void loadCards(int sorting) {
        String method;
        switch (sorting) {
            case SORTING_POPULAR:
                method = NetworkUtilities.getMoviedbMethodPopular();
                break;
            case SORTING_RATED:
                method = NetworkUtilities.getMoviedbMethodRated();
                break;
            default:
                method = NetworkUtilities.getMoviedbMethodPopular();
                break;
        }
        String[] posters = new String[]{ method };
        new FetchFromMovieDbTask().execute(posters);
    }

    public void clearCardView() {
        mScrollListener.resetState();
        mPage = 1;
        mMoviesAdapter.clear();
    }

    public class FetchFromMovieDbTask extends AsyncTask<String[], Void, List<Movie>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Movie> doInBackground(String[]... params) {
            String method = params[0][0];
            Map<String, String> mapping = new HashMap<>();

            mapping.put(NetworkUtilities.getMoviedbLanguageQueryParam(), "en-UK");
            mapping.put(NetworkUtilities.getMoviedbPageQueryParam(), String.valueOf(mPage));

            URL url = NetworkUtilities.buildUrl(method, mapping);

            try {
                String response = NetworkUtilities.getResponseFromHttpUrl(url);
                Log.d(TAG, response);
                JSONObject responseJson = new JSONObject(response);

                return MovieDbJsonUtilities.getPopularMoviesListFromJson(responseJson);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Movie> movieList) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movieList != null) {
                mMoviesAdapter.setMoviesData(movieList);
                mSwipeContainer.setRefreshing(false);
            } else {
                // TODO show error message
                //showErrorMessage();
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        switch (mSorting) {
            case SORTING_POPULAR:
                menu.findItem(R.id.sort_popular).setChecked(true);
                break;
            case SORTING_RATED:
                menu.findItem(R.id.sort_rated).setChecked(true);
                break;
            default:
                menu.findItem(R.id.sort_popular).setChecked(true);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.sort_popular || id == R.id.sort_rated) {
            if (!item.isChecked()) {
                mSorting = item.getOrder();
                item.setChecked(true);
                clearCardView();
                loadCards(mSorting);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
