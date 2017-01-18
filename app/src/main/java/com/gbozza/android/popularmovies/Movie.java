package com.gbozza.android.popularmovies;

/**
 * Object representing a Movie, an entry from the MovieDB API
 */
public class Movie {
    private int id;
    private String posterPath;
    private String overview;
    private String originalTitle;
    private String popularity;
    private String voteAverage;

    private static final String MOVIEDB_POSTER_IMG_URL = "http://image.tmdb.org/t/p/";
    private static final String MOVIEDB_POSTER_WIDHT_W185 = "w185";
    private static final String MOVIEDB_POSTER_WIDHT_W342 = "w342";

    public Movie() {
    }

    public Movie(int id, String posterPath, String overview, String originalTitle,
                 String popularity, String voteAverage) {
        this.id = id;
        this.posterPath = posterPath;
        this.overview = overview;
        this.originalTitle = originalTitle;
        this.popularity = popularity;
        this.voteAverage = voteAverage;
    }

    String buildPosterPath() {
        return MOVIEDB_POSTER_IMG_URL + MOVIEDB_POSTER_WIDHT_W342 + getPosterPath();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }
}
