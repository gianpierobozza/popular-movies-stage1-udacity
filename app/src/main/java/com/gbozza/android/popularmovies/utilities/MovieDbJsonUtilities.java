package com.gbozza.android.popularmovies.utilities;

import android.util.Log;

import com.gbozza.android.popularmovies.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Utilities to manage the JSON Object returned from the MovieDB API
 */
public final class MovieDbJsonUtilities {

    private static final String TMDB_STATUS_CODE = "status_code";
    private static final String TMDB_STATUS_MESSAGE = "status_message";
    private static final int TMDB_STATUS_INVALID_API_KEY = 7;
    private static final int TMDB_STATUS_INVALID_RESOUCE = 34;
    private static final String TMDB_SUCCESS = "success";

    private static final String TAG = MovieDbJsonUtilities.class.getSimpleName();

    /**
     * The method that takes a JSONObject and converts it to a List of Movies
     *
     * @param popularMovies the JSONObject from the service
     * @return a List<Movie> Object, a list containing Movie Objects
     * @throws JSONException
     */
    public static List<Movie> getPopularMoviesListFromJson(JSONObject popularMovies)
            throws JSONException {

        // root keys
        final String TMDB_RESULTS = "results";

        // "results" keys
        final String TMDB_R_POSTER_PATH = "poster_path";
        final String TMDB_R_ID = "id";
        final String TMDB_R_OVERVIEW = "overview";
        final String TMDB_R_ORIGINAL_TITLE = "original_title";
        final String TMDB_R_RELEASE_DATE = "release_date";
        final String TMDB_R_VOTE_AVERAGE = "vote_average";

        List<Movie> parsedMoviesData;

        // Error codes management
        if (popularMovies.has(TMDB_SUCCESS) && !popularMovies.getBoolean(TMDB_SUCCESS)) {
            int errorCode = popularMovies.getInt(TMDB_STATUS_CODE);
            String message = popularMovies.getString(TMDB_STATUS_MESSAGE);

            switch (errorCode) {
                case TMDB_STATUS_INVALID_API_KEY:
                    // Invalid API key provided
                    Log.d(TAG, message);
                    return null;
                case TMDB_STATUS_INVALID_RESOUCE:
                    // Invalid resource
                    Log.d(TAG, message);
                    return null;
                default:
                    // Server probably down
                    return null;
            }
        }

        JSONArray resultsArray = popularMovies.getJSONArray(TMDB_RESULTS);

        parsedMoviesData = new ArrayList<>();

        for (int i = 0; i < resultsArray.length(); i++) {
            int id;
            String posterPath;
            String overview;
            String originalTitle;
            String popularity;
            String voteAverage;

            // Get the JSON object representing one movie result
            JSONObject result = resultsArray.getJSONObject(i);

            id = result.getInt(TMDB_R_ID);
            posterPath = result.getString(TMDB_R_POSTER_PATH);
            overview = result.getString(TMDB_R_OVERVIEW);
            originalTitle = result.getString(TMDB_R_ORIGINAL_TITLE);
            popularity = result.getString(TMDB_R_RELEASE_DATE);
            voteAverage = result.getString(TMDB_R_VOTE_AVERAGE);

            Movie movie = new Movie(id, posterPath, overview, originalTitle, popularity, voteAverage);
            parsedMoviesData.add(movie);
        }

        return parsedMoviesData;
    }
}
