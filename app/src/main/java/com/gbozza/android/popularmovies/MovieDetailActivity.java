package com.gbozza.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gbozza.android.popularmovies.models.Movie;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Activity used to display Movie details, like release date, vote average, etc..
 */
public class MovieDetailActivity extends AppCompatActivity {

    private final static String LABEL_TEXT_TITLE = "Title: ";
    private final static String LABEL_TEXT_VOTE_AVERAGE = "Vote Average: ";
    private final static String LABEL_TEXT_RELEASE_DATE = "Release Date: ";
    private final static String LABEL_TEXT_OVERVIEW = "Overview: ";

    private static final String INTENT_MOVIE_KEY = "movieObject";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent parentIntent = getIntent();
        if (parentIntent != null) {
            if (parentIntent.hasExtra(INTENT_MOVIE_KEY)) {
                getSupportActionBar().setHomeButtonEnabled(true);

                setContentView(R.layout.activity_movie_detail);

                ImageView moviePosterImageView = (ImageView) findViewById(R.id.iv_movie_detail_poster);
                final ProgressBar moviePosterProgressBar = (ProgressBar) findViewById(R.id.pb_movie_detail_poster);
                TextView movieTitleTextView = (TextView) findViewById(R.id.tv_movie_detail_title);
                TextView movieVoteAverageTextView = (TextView) findViewById(R.id.tv_movie_detail_vote_average);
                TextView movieReleaseDateTextView = (TextView) findViewById(R.id.tv_movie_detail_release_date);
                TextView movieOverviewTextView = (TextView) findViewById(R.id.tv_movie_detail_overview);
                final TextView moviePosterErrorTextView = (TextView) findViewById(R.id.tv_movie_detail_poster_error);

                Movie movie = getIntent().getExtras().getParcelable(INTENT_MOVIE_KEY);

                Context context = getApplicationContext();
                Picasso.with(context)
                        .load(movie.buildPosterPath(context))
                        .into(moviePosterImageView, new Callback() {
                            @Override
                            public void onSuccess() {
                                moviePosterProgressBar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError() {
                                moviePosterProgressBar.setVisibility(View.GONE);
                                moviePosterErrorTextView.setRotation(-20);
                                moviePosterErrorTextView.setVisibility(View.VISIBLE);
                            }
                        });

                movieTitleTextView.append(makeBold(LABEL_TEXT_TITLE));
                movieTitleTextView.append(movie.getOriginalTitle());
                movieVoteAverageTextView.append(makeBold(LABEL_TEXT_VOTE_AVERAGE));
                movieVoteAverageTextView.append(movie.getVoteAverage());
                movieReleaseDateTextView.append(makeBold(LABEL_TEXT_RELEASE_DATE));
                movieReleaseDateTextView.append(movie.getReleaseDate());
                movieOverviewTextView.append(makeBold(LABEL_TEXT_OVERVIEW));
                movieOverviewTextView.append(movie.getOverview());

                setTitle(movie.getOriginalTitle());
            }
        }
    }

    /**
     *
     * @param string the text to be styled in bold
     * @return the SpannabelString containing the bold text
     */
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
