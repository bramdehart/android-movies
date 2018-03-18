package nl.bramdehart.movies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
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
        setContentView(R.layout.activity_home);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Set title
        setTitle("Home");

        // Some dummy movies
        ArrayList<Movie> movies = new ArrayList<Movie>();
        movies.add(new Movie("Avengers: Infinity War", "Movie Category", "Movie Description", R.drawable.avengers_infinity_war));
        movies.add(new Movie("Black Panther", "Movie Category", "Movie Description", R.drawable.black_panther));
        movies.add(new Movie("Call Me by Your Name ", "Movie Category", "Movie Description", R.drawable.call_me_by_your_name));
        movies.add(new Movie("Avengers: Infinity War", "Movie Category", "Movie Description", R.drawable.avengers_infinity_war));
        movies.add(new Movie("Black Panther", "Movie Category", "Movie Description", R.drawable.black_panther));
        movies.add(new Movie("Call Me by Your Name ", "Movie Category", "Movie Description", R.drawable.call_me_by_your_name));
        movies.add(new Movie("Avengers: Infinity War", "Movie Category", "Movie Description", R.drawable.avengers_infinity_war));
        movies.add(new Movie("Black Panther", "Movie Category", "Movie Description", R.drawable.black_panther));
        movies.add(new Movie("Call Me by Your Name ", "Movie Category", "Movie Description", R.drawable.call_me_by_your_name));
        movies.add(new Movie("Avengers: Infinity War", "Movie Category", "Movie Description", R.drawable.avengers_infinity_war));
        movies.add(new Movie("Black Panther", "Movie Category", "Movie Description", R.drawable.black_panther));
        movies.add(new Movie("Call Me by Your Name ", "Movie Category", "Movie Description", R.drawable.call_me_by_your_name));
        movies.add(new Movie("Avengers: Infinity War", "Movie Category", "Movie Description", R.drawable.avengers_infinity_war));
        movies.add(new Movie("Black Panther", "Movie Category", "Movie Description", R.drawable.black_panther));
        movies.add(new Movie("Call Me by Your Name ", "Movie Category", "Movie Description", R.drawable.call_me_by_your_name));


        // Init RecyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_movie_list);
        RecyclerViewAdapter rvAdapter = new RecyclerViewAdapter(getApplicationContext(), movies);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        recyclerView.setAdapter(rvAdapter);
    }

}
