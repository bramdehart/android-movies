package nl.bramdehart.movies;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class SearchResultsActivity extends AppCompatActivity {

    private String searchQuery;
    private TextView tvErrorMessage;
    private TextView tvResultsTitle;
    private ProgressBar pbLoadingIndicator;
    private RecyclerView rvMovieList;
    private ArrayList<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        // Bind
        tvErrorMessage = (TextView) findViewById(R.id.tv_error_message);
        pbLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        rvMovieList = (RecyclerView) findViewById(R.id.rv_movie_list);
        tvResultsTitle = (TextView) findViewById(R.id.tv_results_title);

        // Receive data
        Intent intent = getIntent();
        searchQuery = intent.getExtras().getString("SearchQuery");

        URL TMDBSearchURL = NetworkUtils.buildSearchUrl(searchQuery);
        new SearchResultsActivity.TMDBQueryTask().execute(TMDBSearchURL);
    }

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

        @Override
        protected void onPostExecute(String s) {
            pbLoadingIndicator.setVisibility(View.INVISIBLE);
            if (s != null && !s.equals("")) {
                showRecyclerView();
                try {
                    parseMovies(s);
                    populateRecyclerView();
                } catch (JSONException e) {
                    e.printStackTrace();
                    showErrorMessage();
                }
            } else {
                showErrorMessage();
            }
        }
    }

    /**
     *
     * @param moviesJSONString
     */
    private void parseMovies(String moviesJSONString) throws JSONException {
        JSONObject resultJSONObject = new JSONObject(moviesJSONString);
        JSONArray moviesJSONArray = resultJSONObject.getJSONArray("results");
        movies = new ArrayList<Movie>();
        for (int i = 0; i < moviesJSONArray.length(); i++) {
            JSONObject movieJSONObject = new JSONObject(moviesJSONArray.get(i).toString());
            int movieId = movieJSONObject.getInt("id");
            String posterPath = movieJSONObject.getString("poster_path");
            movies.add(new Movie(movieId, posterPath));
        }

        // Set title
        tvResultsTitle.setText(moviesJSONArray.length() + " results for '"+ searchQuery +"'");
    }

    private void populateRecyclerView() {
        RecyclerViewAdapter rvAdapter = new RecyclerViewAdapter(getApplicationContext(), movies);
        rvMovieList.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        rvMovieList.setAdapter(rvAdapter);
    }

    private void showRecyclerView() {
        tvErrorMessage.setVisibility(View.INVISIBLE);
        rvMovieList.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        rvMovieList.setVisibility(View.INVISIBLE);
        tvErrorMessage.setVisibility(View.VISIBLE);
    }
}
