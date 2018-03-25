package nl.bramdehart.movies.data;

import android.provider.BaseColumns;

/**
 * Favorites contract.
 * This class is used to define how the favorites table in the SQL local storage will look like.
 */
public class FavoritesContract {

    /**
     * The properties of the favorites table defined.
     */
    public static final class FavoritesEntry implements BaseColumns {
        public static final String TABLE_NAME = "favorites";
        public static final String COLUMN_MOVIE_ID = "movieId";
        public static final String COLUMN_POSTER_PATH = "posterPath";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }

}
