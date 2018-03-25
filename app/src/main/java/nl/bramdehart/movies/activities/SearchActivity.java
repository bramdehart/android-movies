package nl.bramdehart.movies.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import nl.bramdehart.movies.R;
import nl.bramdehart.movies.helpers.BottomNavigationViewHelper;

public class SearchActivity extends AppCompatActivity {

    private TextView tvTextMessage;
    private EditText etSearchBox;
    private Button btnSearchMovie;

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
                    return true;
                case R.id.navigation_favorites:
                    Intent favoritesIntent = new Intent(getApplicationContext(), FavoritesActivity.class);
                    startActivity(favoritesIntent);
                    return true;
            }
            return false;
        }
    };

    private void submitSearch() {
        Intent intent = new Intent(getApplicationContext(), SearchResultsActivity.class);
        String TMDBQuery = etSearchBox.getText().toString();

        // Passing data to the Search Results activity
        intent.putExtra("SearchQuery", TMDBQuery);
        getApplicationContext().startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Modify bottombar animation and active item
        navigation.setSelectedItemId(R.id.navigation_search);
        BottomNavigationViewHelper.removeShiftMode(navigation);

        etSearchBox = (EditText) findViewById(R.id.et_search_box);
        btnSearchMovie = (Button) findViewById(R.id.btn_search_movie);

        // Set on click listener on button
        btnSearchMovie.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                submitSearch();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
