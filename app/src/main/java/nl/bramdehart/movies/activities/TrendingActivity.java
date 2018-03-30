package nl.bramdehart.movies.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import nl.bramdehart.movies.adapters.MovieRecyclerViewAdapter;
import nl.bramdehart.movies.data.NetworkUtils;
import nl.bramdehart.movies.R;
import nl.bramdehart.movies.models.Movie;

/**
 * Trending activity.
 * The startup activity that show the current trending movies.
 */
public class TrendingActivity extends AppCompatActivity {

    private TextView tvErrorMessage;
    private ProgressBar pbLoadingIndicator;
    private RecyclerView rvMovieList;
    private ArrayList<Movie> movies;
    private RelativeLayout rlMovieResults;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_trending:
                    return true;
                case R.id.navigation_search:
                    Intent searchIntent = new Intent(getApplicationContext(), SearchActivity.class);
                    startActivity(searchIntent);
                    return true;
                case R.id.navigation_favorites:
                    Intent favoritesIntent = new Intent(getApplicationContext(), FavoritesActivity.class);
                    startActivity(favoritesIntent);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trending);

        // Bind components
        rvMovieList = findViewById(R.id.rv_movie_list);
        rvMovieList.setNestedScrollingEnabled(false);
        tvErrorMessage = findViewById(R.id.tv_error_message);
        pbLoadingIndicator = findViewById(R.id.pb_loading_indicator);
        rlMovieResults = findViewById(R.id.rl_movie_results);

        // Set loadingbar color
        pbLoadingIndicator.getIndeterminateDrawable().setColorFilter(Color.parseColor("#b9090b"), PorterDuff.Mode.MULTIPLY);

        showLoadingBar();

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Set bottombar active item
        navigation.setSelectedItemId(R.id.navigation_trending);

        // Starts the query
        if (isOnline()) {
            makeTMDBTrendingQuery();
        } else {
            showErrorMessage();
        }
    }

    /**
     * Makes the query to get current trending movies.
     */
    private void makeTMDBTrendingQuery() {
        URL TMDBTrendingURL = NetworkUtils.buildTrendingUrl();
        new TMDBQueryTask().execute(TMDBTrendingURL);
    }

    /**
     * Shows movie details.
     */
    private void showMovieResults() {
        tvErrorMessage.setVisibility(View.GONE);
        pbLoadingIndicator.setVisibility(View.GONE);
        rlMovieResults.setVisibility(View.VISIBLE);
    }

    /**
     * Shows error message.
     */
    private void showErrorMessage() {
        tvErrorMessage.setVisibility(View.VISIBLE);
        pbLoadingIndicator.setVisibility(View.GONE);
        rlMovieResults.setVisibility(View.GONE);
    }

    /**
     * Shows the loading bar.
     */
    private void showLoadingBar() {
        tvErrorMessage.setVisibility(View.GONE);
        pbLoadingIndicator.setVisibility(View.VISIBLE);
        rlMovieResults.setVisibility(View.GONE);
    }

    /**
     * Checks if there is an internet connection available.
     * @return
     */
    private boolean isOnline() {
        boolean connected = false;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("i", e.getMessage());
        }
        return connected;
    }

    /**
     * Parses the movies JSON string and stores them into the movie array.
     *
     * @param moviesJSONString
     */
    private void parseMovies(String moviesJSONString) throws JSONException {
        JSONObject resultJSONObject = new JSONObject(moviesJSONString);
        JSONArray moviesJSONArray = resultJSONObject.getJSONArray("results");
        movies = new ArrayList<>();

        // Loop throught the JSON array results
        for (int i = 0; i < moviesJSONArray.length(); i++) {
            JSONObject movieJSONObject = new JSONObject(moviesJSONArray.get(i).toString());
            if (!movieJSONObject.isNull("poster_path")) {
                String posterPath = movieJSONObject.getString("poster_path");
                int movieId = movieJSONObject.getInt("id");

                // Add new movie object to the movie array
                movies.add(new Movie(movieId, posterPath));
            }
        }
    }

    /**
     * Populates the recyclerview with the retrieved movies.
     */
    private void populateRecyclerView() {
        MovieRecyclerViewAdapter rvAdapter = new MovieRecyclerViewAdapter(getApplicationContext(), movies);

        // Decide the number of columns based on the screen width
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int spanCount = 2;
        if (width > 1400) {
            spanCount = 5;
        } else if (width > 700) {
            spanCount = 3;
        }

        rvMovieList.setLayoutManager(new GridLayoutManager(getApplicationContext(), spanCount));
        rvMovieList.setAdapter(rvAdapter);
    }

    /**
     * Inner class that takes care of the query task.
     */
    public class TMDBQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String TMDBTrendingResults = null;
            try {
                TMDBTrendingResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return TMDBTrendingResults;
        }

        /**
         * Executes when the API call is finished.
         */
        @Override
        protected void onPostExecute(String s) {
            showLoadingBar();
            try {
                parseMovies(s);
                populateRecyclerView();
                showMovieResults();
            } catch (JSONException e) {
                e.printStackTrace();
                showErrorMessage();
            }
        }
    }
}
