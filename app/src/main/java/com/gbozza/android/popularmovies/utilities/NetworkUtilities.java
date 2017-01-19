package com.gbozza.android.popularmovies.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;

/**
 * Utilities used to communicate with the The Movie DB services over the internet
 */
public final class NetworkUtilities {

    // TODO IMPORTANT: Substitute the actual key before submit with <your_api_key>
    private static final String MOVIEDB_API_KEY = "<your_api_key>";
    private static final String MOVIEDB_API_KEY_QUERY_PARAM = "api_key";
    private static final String MOVIEDB_LANGUAGE_QUERY_PARAM = "language";
    private static final String MOVIEDB_PAGE_QUERY_PARAM = "page";
    private static final String MOVIEDB_API_URL = "https://api.themoviedb.org/3";
    private static final String MOVIEDB_METHOD_POPULAR = "/movie/popular";
    private static final String MOVIEDB_METHOD_RATED = "/movie/top_rated";

    private static final String TAG = NetworkUtilities.class.getSimpleName();

    /**
     * Builds the URL based on method string and a Map of key-value parameters
     *
     * @param method the method to call, will be appended to the api url
     * @param params a Map with the key-values to be appended as query parameters
     * @return a URL for a request to the The Movie DB API service
     */
    public static URL buildUrl(String method, Map<String, String> params) {
        Uri.Builder builder = Uri.parse(MOVIEDB_API_URL + method).buildUpon();
        Log.v(TAG, "Parse url '" + MOVIEDB_API_URL + "' with method '" + method + "'");
        builder.appendQueryParameter(MOVIEDB_API_KEY_QUERY_PARAM, MOVIEDB_API_KEY);
        Log.v(TAG, "Append api key");
        for (Map.Entry<String, String> param : params.entrySet()) {
            builder.appendQueryParameter(param.getKey(), param.getValue());
            Log.v(TAG, "Append param '" + param.getKey() + "' with value '" + param.getValue() + "'");
        }

        Uri uri = builder.build();
        URL url = null;

        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static String getMoviedbLanguageQueryParam() { return MOVIEDB_LANGUAGE_QUERY_PARAM; }

    public static String getMoviedbPageQueryParam() {
        return MOVIEDB_PAGE_QUERY_PARAM;
    }

    public static String getMoviedbMethodPopular() {
        return MOVIEDB_METHOD_POPULAR;
    }

    public static String getMoviedbMethodRated() {
        return MOVIEDB_METHOD_RATED;
    }
}
