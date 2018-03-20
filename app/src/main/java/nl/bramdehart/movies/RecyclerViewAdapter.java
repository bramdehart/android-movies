package nl.bramdehart.movies;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import jp.wasabeef.blurry.Blurry;

/**
 * Created by Bram on 17/03/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private List<Movie> movies;

    public RecyclerViewAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater mInflater = LayoutInflater.from(context);
        View view = mInflater.inflate(R.layout.cardview_item_movie, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Picasso.get().load(movies.get(position).getPosterSmall()).into(holder.ivMoviePoster);

        // Set click listener
        holder.cvMovieItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MovieDetailActivity.class);

                // Passing data to the Movie Detail activity
                intent.putExtra("MovieId", movies.get(position).getMovieId());

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cvMovieItem;
        ImageView ivMoviePoster;

        public MyViewHolder(View itemView) {
            super(itemView);

            cvMovieItem = (CardView) itemView.findViewById(R.id.cv_movie_item);
            ivMoviePoster = (ImageView) itemView.findViewById(R.id.iv_movie_poster);
        }
    }
}
