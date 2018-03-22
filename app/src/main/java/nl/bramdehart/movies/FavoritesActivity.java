package nl.bramdehart.movies;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.MenuItem;

import java.util.ArrayList;

import nl.bramdehart.movies.data.FavoritesContract;
import nl.bramdehart.movies.data.FavoritesDbHelper;

public class FavoritesActivity extends AppCompatActivity {

    private SQLiteDatabase mDatabase;
    private RecyclerView rvMovieList;
    private ArrayList<Movie> movies;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_trending:
                    Intent trendingIntent = new Intent(getApplicationContext(), TrendingActivity.class);
                    startActivity(trendingIntent);
                    return true;
                case R.id.navigation_search:
                    Intent searchIntent = new Intent(getApplicationContext(), SearchActivity.class);
                    startActivity(searchIntent);
                    return true;
                case R.id.navigation_favorites:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        rvMovieList = (RecyclerView) findViewById(R.id.rv_movie_list);
        rvMovieList.setNestedScrollingEnabled(false);

        // Modify bottombar animation and active item
        navigation.setSelectedItemId(R.id.navigation_favorites);
        BottomNavigationViewHelper.removeShiftMode(navigation);

        // Create a database helper
        FavoritesDbHelper dbHelper = new FavoritesDbHelper(this);
        // Get writable database
        mDatabase = dbHelper.getWritableDatabase();
        // Get favorites
        Cursor cursorFavorites = getFavorites();
        // Initialize movies
        initMovies(cursorFavorites);
        populateRecyclerView();
    }

    /**
     * Get the favorites movies from de local database
     * @return
     */
    private Cursor getFavorites() {
        return mDatabase.query(
                FavoritesContract.FavoritesEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                FavoritesContract.FavoritesEntry.COLUMN_TIMESTAMP
        );
    }

    private void populateRecyclerView() {
        MovieRecyclerViewAdapter rvAdapter = new MovieRecyclerViewAdapter(getApplicationContext(), movies);

        // Decide the number of columns based on the screen width
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int spanCount = 2;
        if (width > 1400) {
            spanCount = 4;
        } else if (width > 700) {
            spanCount = 3;
        }

        rvMovieList.setLayoutManager(new GridLayoutManager(getApplicationContext(), spanCount));
        rvMovieList.setAdapter(rvAdapter);
    }

    /**
     * Reads the query result and initialize favorite movies into an array.
     * @param cursorMovies
     */
    private void initMovies(Cursor cursorMovies) {
        try {
            movies = new ArrayList<Movie>();
            while (cursorMovies.moveToNext()) {
                int movieId = cursorMovies.getInt(cursorMovies.getColumnIndex("movieId"));
                String posterPath = cursorMovies.getString(cursorMovies.getColumnIndex("posterPath"));
                movies.add(new Movie(movieId, posterPath));
            }
        } finally {
            cursorMovies.close();
        }
    }

}
