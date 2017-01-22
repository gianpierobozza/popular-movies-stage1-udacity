package com.gbozza.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.gbozza.android.popularmovies.models.Movie;

public class MovieDetailActivity extends AppCompatActivity {

    private TextView mTextDisplay;

    private static final String INTENT_MOVIE_KEY = "movieObject";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        mTextDisplay = (TextView) findViewById(R.id.tv_display_text);

        Intent parentIntent = getIntent();

        if (parentIntent != null) {
            if (parentIntent.hasExtra(INTENT_MOVIE_KEY)) {
                Movie movie = getIntent().getExtras().getParcelable(INTENT_MOVIE_KEY);
                mTextDisplay.setText(movie.getOverview());
            }
        }
    }
}
