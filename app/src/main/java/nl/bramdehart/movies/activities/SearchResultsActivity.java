package nl.bramdehart.movies.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
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
 * Search results activity.
 * Used to display movie search results.
 */
public class SearchResultsActivity extends AppCompatActivity {

    private String searchQuery;
    private TextView tvErrorMessage;
    private TextView tvResultsTitle;
    private RelativeLayout rlMovieResults;
    private ProgressBar pbLoadingIndicator;
    private RecyclerView rvMovieList;
    private ArrayList<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        // Bind components
        tvErrorMessage = findViewById(R.id.tv_error_message);
        pbLoadingIndicator = findViewById(R.id.pb_loading_indicator);
        tvResultsTitle = findViewById(R.id.tv_results_title);
        rlMovieResults = findViewById(R.id.rl_movie_results);

        rvMovieList = findViewById(R.id.rv_movie_list);
        rvMovieList.setNestedScrollingEnabled(false);

        // Set loadingbar color
        pbLoadingIndicator.getIndeterminateDrawable().setColorFilter(Color.parseColor("#b9090b"), PorterDuff.Mode.MULTIPLY);

        showLoadingBar();

        // Get received data from previous intent
        Intent intent = getIntent();
        searchQuery = intent.getExtras().getString("SearchQuery");

        // Start search query
        URL TMDBSearchURL = NetworkUtils.buildSearchUrl(searchQuery);
        if (isOnline()) {
            new SearchResultsActivity.TMDBQueryTask().execute(TMDBSearchURL);
        } else {
            showErrorMessage();
        }
    }

    /**
     * Inner class that takes care of the query task.
     */
    public class TMDBQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pbLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String TMDBSearchResults = null;
            try {
                TMDBSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return TMDBSearchResults;
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

    /**
     * Parses the movie JSON string and inserts the results as a new Movie object into the Movie array.
     *
     * @param moviesJSONString
     * The JSON string containing the results.
     */
    private void parseMovies(String moviesJSONString) throws JSONException {
        JSONObject resultJSONObject = new JSONObject(moviesJSONString);
        JSONArray moviesJSONArray = resultJSONObject.getJSONArray("results");
        movies = new ArrayList<Movie>();

        // Loop through json array
        for (int i = 0; i < moviesJSONArray.length(); i++) {
            JSONObject movieJSONObject = new JSONObject(moviesJSONArray.get(i).toString());
            if (!movieJSONObject.isNull("poster_path")) {
                int movieId = movieJSONObject.getInt("id");
                String posterPath = movieJSONObject.getString("poster_path");

                // Add movie object
                movies.add(new Movie(movieId, posterPath));
            }
        }

        // Set result count
        tvResultsTitle.setText(movies.size() + " " +  getResources().getString(R.string.results_heading) + " '" + searchQuery + "'");
    }

    /**
     * Populates the recyclerview with the retrieved movies.
     */
    private void populateRecyclerView() {
        MovieRecyclerViewAdapter rvAdapter = new MovieRecyclerViewAdapter(getApplicationContext(), movies);
        rvMovieList.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        rvMovieList.setAdapter(rvAdapter);
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
}
