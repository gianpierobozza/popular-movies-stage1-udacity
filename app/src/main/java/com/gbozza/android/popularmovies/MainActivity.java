package com.gbozza.android.popularmovies;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.gbozza.android.popularmovies.utilities.MovieDbJsonUtilities;
import com.gbozza.android.popularmovies.utilities.NetworkUtilities;

import org.json.JSONObject;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.MoviesAdapterOnClickHandler {

    private MoviesAdapter mMoviesAdapter;

    private ProgressBar mLoadingIndicator;

    private static final int SPAN_COUNT = 2;

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, SPAN_COUNT, GridLayoutManager.VERTICAL, false);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rv_posters);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        mRecyclerView.setHasFixedSize(true);
        mMoviesAdapter = new MoviesAdapter(this);
        mRecyclerView.setAdapter(mMoviesAdapter);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        loadCards();
    }

    public void loadCards() {
        String[] posters = new String[]{ NetworkUtilities.getMoviedbMethodPopularmovies() };
        new FetchFromMovieDbTask().execute(posters);
    }

    @Override
    public void onClick(String clickedItem) {
        // TODO pass movie id to the movie details intent
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
            Map <String, String> mapping = new HashMap<>();
            mapping.put("language", "en-US");

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
            } else {
                // TODO show error message
                //showErrorMessage();
            }
        }
    }
}
