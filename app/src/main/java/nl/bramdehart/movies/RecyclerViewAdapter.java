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

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Bram on 17/03/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<Movie> mData;

    public RecyclerViewAdapter(Context mContext, List<Movie> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View view = mInflater.inflate(R.layout.cardview_item_movie, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.movie_title.setText(mData.get(position).getTitle());
        holder.movie_poster.setImageResource(mData.get(position).getPoster());

        // Set click listener
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MovieDetailActivity.class);

                // Passing data to the Movie Detail activity
                intent.putExtra("Title", mData.get(position).getTitle());
                intent.putExtra("Category", mData.get(position).getCategory());
                intent.putExtra("Description", mData.get(position).getDescription());
                intent.putExtra("Poster", mData.get(position).getPoster());

                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView movie_title;
        ImageView movie_poster;

        public MyViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.cardview_id);
            movie_title = (TextView) itemView.findViewById(R.id.movie_title_id);
            movie_poster = (ImageView) itemView.findViewById(R.id.movie_poster_id);
        }
    }
}
