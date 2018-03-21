package nl.bramdehart.movies;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Bram on 21/03/2018.
 */

public class CastRecyclerViewAdapter extends RecyclerView.Adapter<CastRecyclerViewAdapter.MyViewHolder> {
    private Context context;
    private List<Cast> cast;

    public CastRecyclerViewAdapter(Context context, List<Cast> cast) {
        this.context = context;
        this.cast = cast;
    }

    @Override
    public CastRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater mInflater = LayoutInflater.from(context);
        View view = mInflater.inflate(R.layout.cast_item, parent, false);

        return new CastRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CastRecyclerViewAdapter.MyViewHolder holder, final int position) {
        Picasso.get().load(cast.get(position).getProfileUrl()).into(holder.ivProfile);
        holder.tvName.setText(cast.get(position).getName());
        holder.tvCharacter.setText(cast.get(position).getCharacter());
    }

    @Override
    public int getItemCount() {
        return cast.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout clCastItem;
        ImageView ivProfile;
        TextView tvName;
        TextView tvCharacter;

        public MyViewHolder(View itemView) {
            super(itemView);
            clCastItem = (ConstraintLayout) itemView.findViewById(R.id.cl_cast_item);
            ivProfile = (ImageView) itemView.findViewById(R.id.iv_cast_profile);
            tvName = (TextView) itemView.findViewById(R.id.tv_cast_name);
            tvCharacter = (TextView) itemView.findViewById(R.id.tv_cast_character);

            Animation fadeInAnimation = AnimationUtils.loadAnimation(clCastItem.getContext(), R.anim.fade_in);
            ivProfile.startAnimation(fadeInAnimation); //Set animation to your ImageView
        }
    }
}
