package com.gbozza.android.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Object representing a Movie, an entry from the MovieDB API
 */
public class Movie implements Parcelable {
    private int id;
    private String posterPath;
    private String overview;
    private String originalTitle;
    private String popularity;
    private String voteAverage;

    private static final String MOVIEDB_POSTER_IMG_URL = "http://image.tmdb.org/t/p/";
    private static final String MOVIEDB_POSTER_WIDHT_W185 = "w185";
    private static final String MOVIEDB_POSTER_WIDHT_W342 = "w342";

    public Movie(int id, String posterPath, String overview, String originalTitle,
                 String popularity, String voteAverage) {
        this.id = id;
        this.posterPath = posterPath;
        this.overview = overview;
        this.originalTitle = originalTitle;
        this.popularity = popularity;
        this.voteAverage = voteAverage;
    }

    private Movie(Parcel parcel) {
        id = parcel.readInt();
        posterPath = parcel.readString();
        overview = parcel.readString();
        originalTitle = parcel.readString();
        popularity = parcel.readString();
        voteAverage = parcel.readString();
    }

    public String buildPosterPath() {
        return MOVIEDB_POSTER_IMG_URL + MOVIEDB_POSTER_WIDHT_W342 + getPosterPath();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(id);
        parcel.writeString(posterPath);
        parcel.writeString(overview);
        parcel.writeString(originalTitle);
        parcel.writeString(popularity);
        parcel.writeString(voteAverage);
    }

    public final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }

    };

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
