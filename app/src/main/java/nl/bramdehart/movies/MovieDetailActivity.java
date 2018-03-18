package nl.bramdehart.movies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MovieDetailActivity extends AppCompatActivity {

    private TextView tvTitle;
    private ImageView ivPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        tvTitle = (TextView) findViewById(R.id.tv_movie_title);
        ivPoster = (ImageView) findViewById(R.id.iv_movie_poster);

        // Receive data
        Intent intent = getIntent();
        String Title = intent.getExtras().getString("Title");
        int Poster = intent.getExtras().getInt("Poster");

        // Setting values
        setTitle(Title);
        tvTitle.setText(Title);
        ivPoster.setImageResource(Poster);
    }
}
