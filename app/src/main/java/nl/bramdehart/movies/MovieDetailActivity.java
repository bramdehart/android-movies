package nl.bramdehart.movies;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Rating;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import nl.bramdehart.movies.data.FavoritesContract;
import nl.bramdehart.movies.data.FavoritesDbHelper;

public class MovieDetailActivity extends AppCompatActivity {


    private TextView tvMovieTitle;
    private ImageView ivMoviePoster;
    private TextView tvMovieGenres;
    private TextView tvMovieProductionCompanies;
    private TextView tvMovieRunTime;
    private TextView tvMovieOverview;
    private TextView tvMovieReleaseDate;
    private TextView tvErrorMessage;
    private Button btnFavorites;
    private ProgressBar pbLoadingIndicator;
    private LinearLayout llMovieInfoHolder;
    private RatingBar rbRatingBar;
    private RecyclerView rvCast;
    private RecyclerView rvCrew;
    private int movieId;

    private Movie movie;
    private SQLiteDatabase mDatabase;

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
        btnFavorites = (Button) findViewById(R.id.btn_favorites);
        rvCast = (RecyclerView) findViewById(R.id.rv_cast);
        rvCrew = (RecyclerView) findViewById(R.id.rv_crew);

        // Receive data
        Intent intent = getIntent();
        movieId = intent.getExtras().getInt("MovieId");
        makeTMDBMovieDetailQuery(movieId);

        // Create a database helper
        FavoritesDbHelper dbHelper = new FavoritesDbHelper(this);
        // Get writable database
        mDatabase = dbHelper.getWritableDatabase();

        // Set button favorites text
        String txtBtnFavorites;
        if (isInFavorites()) {
            txtBtnFavorites = "Remove from my favorites";
        } else {
            txtBtnFavorites = "Add to my favorites";
        }
        btnFavorites.setText(txtBtnFavorites);

        // Set on click listener on favorite button
        btnFavorites.setOnClickListener(new View.OnClickListener() {
            int duration = Toast.LENGTH_LONG;

            public void onClick(View v) {
                if (isInFavorites() && removeFromFavorites()) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Removed '" + movie.getTitle() + "' from your favorites", duration);
                    toast.show();
                    btnFavorites.setText("Add to my favorites");
                } else if (addToFavorites()) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Added '" + movie.getTitle() + "' to your favorites", duration);
                    toast.show();
                    btnFavorites.setText("Remove from my favorites");
                }
            }
        });
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
        int movieId = movieJSONObject.getInt("id");
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

        JSONObject castsJSONObject = movieJSONObject.getJSONObject("casts");

        // Get cast
        ArrayList<Cast> cast = new ArrayList<Cast>();
        JSONArray castJSONArray = castsJSONObject.getJSONArray("cast");
        for (int i = 0; i < castJSONArray.length(); i++) {
            JSONObject castJSONObject = castJSONArray.getJSONObject(i);
            cast.add(new Cast(castJSONObject.getString("name"), castJSONObject.getString("character"), castJSONObject.getString("profile_path")));
        }

        // Get crew
        ArrayList<Crew> crew = new ArrayList<Crew>();
        JSONArray crewJSONArray = castsJSONObject.getJSONArray("crew");
        for (int i = 0; i < crewJSONArray.length(); i++) {
            JSONObject crewJSONObject = crewJSONArray.getJSONObject(i);
            crew.add(new Crew(crewJSONObject.getString("name"), crewJSONObject.getString("job"), crewJSONObject.getString("profile_path")));
        }

        // Initialize movie
        movie = new Movie(movieId, movieTitle, moviePosterPath, movieBackDropPath, movieRunTime, movieRating, movieOverview, movieReleaseDate, movieGenres, movieProductionCompanies, cast, crew);

    }

    private void bindMovieData() {
        // Set activity components
        ImageView headerBackdrop = (ImageView) findViewById(R.id.header_backdrop);
        Picasso.get().load(movie.getBackDropLarge()).into(headerBackdrop);
        setTitle(movie.getTitle());

        // Set text values
        tvMovieTitle.setText(movie.getTitle());
        tvMovieRunTime.setText("Duration: " +String.valueOf(movie.getRunTime()) + " min");
        tvMovieOverview.setText(movie.getOverview());
        tvMovieReleaseDate.setText("Release: " + movie.getReleaseDate());

        // Load images
        Picasso.get().load(movie.getPosterLarge()).into(ivMoviePoster);

        // Animations
        Animation fadeInAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        Animation moveUpAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_up);
        ivMoviePoster.startAnimation(moveUpAnimation); //Set animation to your ImageView
        headerBackdrop.startAnimation(fadeInAnimation);

        // Set rating
        rbRatingBar.setRating((float) (movie.getRating() / 2));

        // Append genres
        ArrayList<String> genres = movie.getGenres();
        tvMovieGenres.setText("Genres: ");
        if (genres.size() == 0) {
            tvMovieGenres.append("Unknown");
        } else {
            for (int i = 0; i < genres.size(); i++) {
                if (i != 0) {
                    tvMovieGenres.append(", ");
                }
                tvMovieGenres.append(genres.get(i));
            }
        }

        // Append production companies
        ArrayList<String> productionCompanies = movie.getProductionCompanies();
        tvMovieProductionCompanies.setText("Producers: ");
        if (productionCompanies.size() == 0) {
            tvMovieProductionCompanies.append("Unknown");
        } else {
            for (int i = 0; i < productionCompanies.size(); i++) {
                if (i != 0) {
                    tvMovieProductionCompanies.append(", ");
                }
                tvMovieProductionCompanies.append(productionCompanies.get(i));
            }
        }


        CastRecyclerViewAdapter castAdapter = new CastRecyclerViewAdapter(getApplicationContext(), movie.getCast());
        LinearLayoutManager castLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvCast.setLayoutManager(castLayoutManager);
        rvCast.setAdapter(castAdapter);

        CrewRecyclerViewAdapter crewAdapter = new CrewRecyclerViewAdapter(getApplicationContext(), movie.getCrew());
        LinearLayoutManager crewLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvCrew.setLayoutManager(crewLayoutManager);
        rvCrew.setAdapter(crewAdapter);

    }

    private Boolean isInFavorites() {
        ContentValues values = new ContentValues();
        values.put(FavoritesContract.FavoritesEntry.COLUMN_MOVIE_ID, movieId);
        Cursor mCursor = mDatabase.rawQuery(
                "SELECT * FROM " + FavoritesContract.FavoritesEntry.TABLE_NAME +
                        " WHERE " + FavoritesContract.FavoritesEntry.COLUMN_MOVIE_ID + "= " + movieId
                , null);

        return mCursor.moveToFirst();
    }

    private Boolean addToFavorites() {
        ContentValues values = new ContentValues();
        // add values to record keys
        values.put(FavoritesContract.FavoritesEntry.COLUMN_MOVIE_ID, movieId);
        values.put(FavoritesContract.FavoritesEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
        return mDatabase.insert(FavoritesContract.FavoritesEntry.TABLE_NAME, null, values) > 0;
    }

    private Boolean removeFromFavorites() {
        return mDatabase.delete(FavoritesContract.FavoritesEntry.TABLE_NAME, FavoritesContract.FavoritesEntry.COLUMN_MOVIE_ID + " = " + movieId, null) > 0;
    }

    public class TMDBQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String TMDBResults = null;
            try {
                TMDBResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return TMDBResults;
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
