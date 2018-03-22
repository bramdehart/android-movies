package nl.bramdehart.movies;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Bram on 22/03/2018.
 */

public class CrewRecyclerViewAdapter extends RecyclerView.Adapter<CrewRecyclerViewAdapter.MyViewHolder>{

    private Context context;
    private List<Crew> crew;

    public CrewRecyclerViewAdapter(Context context, List<Crew> crew) {
        this.context = context;
        this.crew = crew;
    }

    @Override
    public CrewRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater mInflater = LayoutInflater.from(context);
        View view = mInflater.inflate(R.layout.crew_item, parent, false);

        return new CrewRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CrewRecyclerViewAdapter.MyViewHolder holder, final int position) {
        if (crew.get(position).getProfilePath() == "null") {
            Picasso.get().load(R.drawable.profile_placeholder).into(holder.ivProfile);
        } else {
            Picasso.get().load(crew.get(position).getProfileUrl()).into(holder.ivProfile);
        }

        holder.tvName.setText(crew.get(position).getName());
        holder.tvJob.setText(crew.get(position).getJob());
    }

    @Override
    public int getItemCount() {
        return crew.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout clCrewItem;
        ImageView ivProfile;
        TextView tvName;
        TextView tvJob;

        public MyViewHolder(View itemView) {
            super(itemView);
            clCrewItem = (ConstraintLayout) itemView.findViewById(R.id.cl_crew_item);
            ivProfile = (ImageView) itemView.findViewById(R.id.iv_crew_profile);
            tvName = (TextView) itemView.findViewById(R.id.tv_crew_name);
            tvJob = (TextView) itemView.findViewById(R.id.tv_crew_job);

            Animation fadeInAnimation = AnimationUtils.loadAnimation(clCrewItem.getContext(), R.anim.fade_in);
            ivProfile.startAnimation(fadeInAnimation); //Set animation to your ImageView
        }
    }
}