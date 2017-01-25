package com.gbozza.android.popularmovies.tasks;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.gbozza.android.popularmovies.R;
import com.gbozza.android.popularmovies.fragments.MovieListFragment;
import com.gbozza.android.popularmovies.models.Movie;
import com.gbozza.android.popularmovies.utilities.MovieDbJsonUtilities;
import com.gbozza.android.popularmovies.utilities.NetworkUtilities;

import org.json.JSONObject;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * The background worker that executes the calls to the MovieDB service
 */
public class FetchFromMovieDbTask extends AsyncTask<String[], Void, List<Movie>> {
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        MovieListFragment.mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    protected List<Movie> doInBackground(String[]... params) {
        String method = params[0][0];
        String page = params[0][1];
        Map<String, String> mapping = new HashMap<>();

        mapping.put(NetworkUtilities.getMoviedbLanguageQueryParam(), MovieListFragment.MOVIEDB_LANGUAGE);
        mapping.put(NetworkUtilities.getMoviedbPageQueryParam(), String.valueOf(page));

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
        MovieListFragment.mLoadingIndicator.setVisibility(View.INVISIBLE);
        if (movieList != null) {
            MovieListFragment.mMoviesAdapter.setMoviesData(movieList);
            MovieListFragment.mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        } else {

            MovieListFragment.showErrorMessage(R.string.error_moviedb_list);
        }
        MovieListFragment.mSwipeContainer.setRefreshing(false);
    }
}