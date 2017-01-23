package com.gbozza.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.gbozza.android.popularmovies.models.Movie;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    private final static String LABEL_RELEASE_VOTE_AVERAGE = "Vote Average: ";
    private final static String LABEL_RELEASE_DATE_TEXT = "Release Date: ";
    private final static String LABEL_OVERVIEW_TEXT = "Overview: ";

    private static final String INTENT_MOVIE_KEY = "movieObject";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setHomeButtonEnabled(true);

        setContentView(R.layout.activity_movie_detail);
        ImageView moviePosterImageView = (ImageView) findViewById(R.id.iv_movie_detail_poster);
        TextView movieTitleTextView = (TextView) findViewById(R.id.tv_movie_detail_title);
        TextView movieVoteAverageTextView = (TextView) findViewById(R.id.tv_movie_detail_vote_average);
        TextView movieReleaseDateTextView = (TextView) findViewById(R.id.tv_movie_detail_release_date);
        TextView movieOverviewTextView = (TextView) findViewById(R.id.tv_movie_detail_overview);

        Intent parentIntent = getIntent();
        if (parentIntent != null) {
            if (parentIntent.hasExtra(INTENT_MOVIE_KEY)) {
                Movie movie = getIntent().getExtras().getParcelable(INTENT_MOVIE_KEY);

                Context context = getApplicationContext();
                Picasso.with(context)
                        .load(movie.buildPosterPath(context))
                        .into(moviePosterImageView);

                movieTitleTextView.setText(movie.getOriginalTitle());
                movieVoteAverageTextView.append(makeBold(LABEL_RELEASE_VOTE_AVERAGE));
                movieVoteAverageTextView.append(movie.getVoteAverage());
                movieReleaseDateTextView.append(makeBold(LABEL_RELEASE_DATE_TEXT));
                movieReleaseDateTextView.append(movie.getReleaseDate());
                movieOverviewTextView.append(makeBold(LABEL_OVERVIEW_TEXT));
                movieOverviewTextView.append(movie.getOverview());
            }
        }
    }

    private SpannableString makeBold(String string) {
        SpannableString boldText = new SpannableString(string);
        boldText.setSpan(new StyleSpan(Typeface.BOLD), 0, boldText.length(), 0);
        return boldText;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
