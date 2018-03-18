package nl.bramdehart.movies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

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
        holder.movieTitle.setText(movies.get(position).getTitle());
        holder.moviePoster.setImageResource(movies.get(position).getPoster());

        // Set click listener
        holder.movieItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MovieDetailActivity.class);

                // Passing data to the Movie Detail activity
                intent.putExtra("Title", movies.get(position).getTitle());
                intent.putExtra("Category", movies.get(position).getCategory());
                intent.putExtra("Description", movies.get(position).getDescription());
                intent.putExtra("Poster", movies.get(position).getPoster());

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView movieItem;
        TextView movieTitle;
        ImageView moviePoster;

        public MyViewHolder(View itemView) {
            super(itemView);

            movieItem = (CardView) itemView.findViewById(R.id.movie_item);
            movieTitle = (TextView) itemView.findViewById(R.id.movie_title);
            moviePoster = (ImageView) itemView.findViewById(R.id.movie_poster);
        }
    }
}
