package nl.bramdehart.movies;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Change activity title
        getActivity().setTitle("Home");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ArrayList<Movie> movies = new ArrayList<Movie>();
        movies.add(new Movie("Avengers: Infinity War", "Movie Category", "Movie Description", R.drawable.avengers_infinity_war));
        movies.add(new Movie("Black Panther", "Movie Category", "Movie Description", R.drawable.black_panther));
        movies.add(new Movie("Call Me by Your Name ", "Movie Category", "Movie Description", R.drawable.call_me_by_your_name));
        movies.add(new Movie("Get Out", "Movie Category", "Movie Description", R.drawable.get_out));
        movies.add(new Movie("Jumanji: Welcome to the Jungle", "Movie Category", "Movie Description", R.drawable.jumanji_welcome_to_the_jungle));
        movies.add(new Movie("Red Sparrow", "Movie Category", "Movie Description", R.drawable.red_sparrow));
        movies.add(new Movie("The Shape of Water", "Movie Category", "Movie Description", R.drawable.the_shape_of_water));
        movies.add(new Movie("Three Billboards Outside Ebbing Missouri", "Movie Category", "Movie Description", R.drawable.three_billboards_outside_ebbing_missouri));
        movies.add(new Movie("Tob Raider", "Movie Category", "Movie Description", R.drawable.tomb_raider));
        movies.add(new Movie("Avengers: Infinity War", "Movie Category", "Movie Description", R.drawable.avengers_infinity_war));
        movies.add(new Movie("Black Panther", "Movie Category", "Movie Description", R.drawable.black_panther));
        movies.add(new Movie("Call Me by Your Name ", "Movie Category", "Movie Description", R.drawable.call_me_by_your_name));
        movies.add(new Movie("Get Out", "Movie Category", "Movie Description", R.drawable.get_out));
        movies.add(new Movie("Jumanji: Welcome to the Jungle", "Movie Category", "Movie Description", R.drawable.jumanji_welcome_to_the_jungle));
        movies.add(new Movie("Red Sparrow", "Movie Category", "Movie Description", R.drawable.red_sparrow));
        movies.add(new Movie("The Shape of Water", "Movie Category", "Movie Description", R.drawable.the_shape_of_water));
        movies.add(new Movie("Three Billboards Outside Ebbing Missouri", "Movie Category", "Movie Description", R.drawable.three_billboards_outside_ebbing_missouri));
        movies.add(new Movie("Tob Raider", "Movie Category", "Movie Description", R.drawable.tomb_raider));
        movies.add(new Movie("Avengers: Infinity War", "Movie Category", "Movie Description", R.drawable.avengers_infinity_war));
        movies.add(new Movie("Black Panther", "Movie Category", "Movie Description", R.drawable.black_panther));
        movies.add(new Movie("Call Me by Your Name ", "Movie Category", "Movie Description", R.drawable.call_me_by_your_name));
        movies.add(new Movie("Get Out", "Movie Category", "Movie Description", R.drawable.get_out));
        movies.add(new Movie("Jumanji: Welcome to the Jungle", "Movie Category", "Movie Description", R.drawable.jumanji_welcome_to_the_jungle));
        movies.add(new Movie("Red Sparrow", "Movie Category", "Movie Description", R.drawable.red_sparrow));
        movies.add(new Movie("The Shape of Water", "Movie Category", "Movie Description", R.drawable.the_shape_of_water));
        movies.add(new Movie("Three Billboards Outside Ebbing Missouri", "Movie Category", "Movie Description", R.drawable.three_billboards_outside_ebbing_missouri));
        movies.add(new Movie("Tob Raider", "Movie Category", "Movie Description", R.drawable.tomb_raider));

        RecyclerView mrv = (RecyclerView) view.findViewById(R.id.movie_list);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this.getActivity(), movies);
        mrv.setLayoutManager(new GridLayoutManager(this.getActivity(), 3));
        mrv.setAdapter(myAdapter);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            Toast.makeText(context, "Home Fragment Attached", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
