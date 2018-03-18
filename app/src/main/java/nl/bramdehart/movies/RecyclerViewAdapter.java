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
        holder.tvMovieTitle.setText(movies.get(position).getTitle());
        holder.ivMoviePoster.setImageResource(movies.get(position).getPoster());

        // Set click listener
        holder.cvMovieItem.setOnClickListener(new View.OnClickListener() {
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
        CardView cvMovieItem;
        TextView tvMovieTitle;
        ImageView ivMoviePoster;

        public MyViewHolder(View itemView) {
            super(itemView);

            cvMovieItem = (CardView) itemView.findViewById(R.id.cv_movie_item);
            tvMovieTitle = (TextView) itemView.findViewById(R.id.tv_movie_title);
            ivMoviePoster = (ImageView) itemView.findViewById(R.id.iv_movie_poster);
        }
    }
}
