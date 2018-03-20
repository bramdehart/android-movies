package nl.bramdehart.movies;

import android.content.Intent;
import android.media.Rating;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MovieDetailActivity extends AppCompatActivity {


    private TextView tvMovieTitle;
    private ImageView ivMoviePoster;
    private TextView tvMovieGenres;
    private TextView tvMovieProductionCompanies;
    private TextView tvMovieRunTime;
    private TextView tvMovieOverview;
    private TextView tvMovieReleaseDate;
    private TextView tvErrorMessage;
    private ProgressBar pbLoadingIndicator;
    private LinearLayout llMovieInfoHolder;
    private RatingBar rbRatingBar;

    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        tvMovieTitle = (TextView) findViewById(R.id.tv_movie_title);
        ivMoviePoster = (ImageView) findViewById(R.id.iv_movie_poster);
        tvMovieGenres = (TextView) findViewById(R.id.tv_movie_genres);
        tvMovieProductionCompanies = (TextView) findViewById(R.id.tv_movie_production_companies);
        tvMovieRunTime = (TextView) findViewById(R.id.tv_movie_runtime);
        tvMovieReleaseDate = (TextView) findViewById(R.id.tv_movie_release_date);
        tvMovieOverview = (TextView) findViewById(R.id.tv_movie_overview);
        tvErrorMessage = (TextView) findViewById(R.id.tv_error_message);
        pbLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        llMovieInfoHolder = (LinearLayout) findViewById(R.id.ll_movie_info_holder);
        rbRatingBar = (RatingBar) findViewById(R.id.rb_movie_rating);

        // Receive data
        Intent intent = getIntent();
        int movieId = intent.getExtras().getInt("MovieId");
        makeTMDBMovieDetailQuery(movieId);
    }

    private void makeTMDBMovieDetailQuery(int movieId) {
        URL TMDBMovieDetailURL = NetworkUtils.buildMovieDetailUrl(movieId);
        new MovieDetailActivity.TMDBQueryTask().execute(TMDBMovieDetailURL);
    }

    private void showMovieDetails() {
        tvErrorMessage.setVisibility(View.INVISIBLE);
        llMovieInfoHolder.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        llMovieInfoHolder.setVisibility(View.INVISIBLE);
        tvErrorMessage.setVisibility(View.VISIBLE);
    }

    /**
     * @param movieJSONString
     */
    private void parseMovieData(String movieJSONString) throws JSONException {

        // Get JSON values
        JSONObject movieJSONObject = new JSONObject(movieJSONString);
        String movieTitle = movieJSONObject.getString("title");
        String moviePosterPath = movieJSONObject.getString("poster_path");
        String movieBackDropPath = movieJSONObject.getString("backdrop_path");
        int movieRunTime = movieJSONObject.getInt("runtime");
        double movieRating = movieJSONObject.getDouble("vote_average");
        String movieOverview = movieJSONObject.getString("overview");
        String movieReleaseDate = movieJSONObject.getString("release_date");

        // Get movie categories
        ArrayList<String> movieGenres = new ArrayList<String>();
        JSONArray movieGenresJSONArray = movieJSONObject.getJSONArray("genres");
        for (int i = 0; i < movieGenresJSONArray.length(); i++) {
            JSONObject movieGenreJSONObject = movieGenresJSONArray.getJSONObject(i);
            movieGenres.add(movieGenreJSONObject.getString("name"));
        }

        // Get production companies
        ArrayList<String> movieProductionCompanies = new ArrayList<String>();
        JSONArray movieProductionCompaniesJSONArray = movieJSONObject.getJSONArray("production_companies");
        for (int i = 0; i < movieProductionCompaniesJSONArray.length(); i++) {
            JSONObject movieProductionCompanyJSONObject = movieProductionCompaniesJSONArray.getJSONObject(i);
            movieProductionCompanies.add(movieProductionCompanyJSONObject.getString("name"));
        }

        // Initialize movie
        movie = new Movie(movieTitle, moviePosterPath, movieBackDropPath, movieRunTime, movieRating, movieOverview, movieReleaseDate, movieGenres, movieProductionCompanies);

    }

    private void bindMovieData() {
        // Set activity components
        ImageView headerBackdrop = (ImageView)findViewById(R.id.header_backdrop);
        Picasso.get().load(movie.getBackDropLarge()).into(headerBackdrop);
        setTitle(movie.getTitle());

        // Set text values
        tvMovieTitle.setText(movie.getTitle());
        tvMovieRunTime.setText(String.valueOf(movie.getRunTime() + "min"));
        tvMovieOverview.setText(movie.getOverview());
        tvMovieReleaseDate.setText(movie.getReleaseDate());

        // Load images
        Picasso.get().load(movie.getPosterLarge()).into(ivMoviePoster);

        // Set rating
        rbRatingBar.setRating((float) (movie.getRating() / 2));

        // Append genres
        ArrayList<String> genres = movie.getGenres();
        tvMovieGenres.setText("Genres: ");
        for (int i = 0; i < genres.size(); i++) {
            if (i != 0) {
                tvMovieGenres.append(", ");
            }
            tvMovieGenres.append(genres.get(i));
        }

        // Append production companies
        ArrayList<String> productionCompanies = movie.getProductionCompanies();
        tvMovieProductionCompanies.setText("Producers: ");
        for (int i = 0; i < productionCompanies.size(); i++) {
            if (i != 0) {
                tvMovieProductionCompanies.append(", ");
            }
            tvMovieProductionCompanies.append(productionCompanies.get(i));
        }
    }

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

        @Override
        protected void onPostExecute(String s) {
            pbLoadingIndicator.setVisibility(View.INVISIBLE);
            if (s != null && !s.equals("")) {
                showMovieDetails();
                try {
                    parseMovieData(s);
                    bindMovieData();

                } catch (JSONException e) {
                    e.printStackTrace();
                    showErrorMessage();
                }
            } else {
                showErrorMessage();
            }
        }
    }
}