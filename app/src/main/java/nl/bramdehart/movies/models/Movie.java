package nl.bramdehart.movies.models;

import java.util.ArrayList;

import nl.bramdehart.movies.models.Cast;
import nl.bramdehart.movies.models.Crew;

/**
 * Created by Bram on 17/03/2018.
 */

public class Movie {

    private int movieId;
    private String title;
    private String posterPath;
    private String posterSmall;
    private String posterLarge;
    private String backDropLarge;
    private int runTime;
    private double rating;
    private String overview;
    private String releaseDate;
    private String trailerUrl;
    private ArrayList<String> genres;
    private ArrayList<String> productionCompanies;
    private ArrayList<Crew> crew;
    private ArrayList<Cast> cast;
    private final static String TMDB_IMG_BASE_URL = "http://image.tmdb.org/t/p/";
    private final static String YT_MOVIE_BASE_URL = "https://www.youtube.com/watch";

    public Movie() {
    }

    /**
     * Inits a movie used by the movie recycler view.
     * @param movieId the movie id
     * @param posterPath the movie poster url
     */
    public Movie(int movieId, String posterPath) {
        this.movieId = movieId;
        this.setPosters(posterPath);
    }

    /**
     * Inits a movie used in the detail activity.
     * @param movieId the movie id
     * @param title the movie title
     * @param posterPath the movie poster url
     * @param backDropPath the movie backdrop url
     * @param runTime the movie runtime
     * @param rating the movie rating
     * @param overview the movie overview
     * @param releaseDate the movie release date
     * @param genres the movie genres
     * @param productionCompanies the movie production companies
     * @param cast the movie cast
     * @param crew the movie crew
     * @param trailerId the movie YouTube id
     */
    public Movie(int movieId, String title, String posterPath, String backDropPath, int runTime, double rating, String overview, String releaseDate, ArrayList<String> genres, ArrayList<String> productionCompanies, ArrayList<Cast> cast, ArrayList<Crew> crew, String trailerId) {
        this.movieId = movieId;
        this.title = title;
        this.runTime = runTime;
        this.rating = rating;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.genres = genres;
        this.productionCompanies = productionCompanies;
        this.crew = crew;
        this.cast = cast;
        this.posterPath = posterPath;
        setPosters(posterPath);
        setBackDrops(backDropPath);
        if (trailerId != null) {
            setTrailerUrl(trailerId);
        }
    }

    public int getMovieId() {
        return movieId;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterSmall() {
        return posterSmall;
    }

    public String getPosterLarge() {
        return posterLarge;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackDropLarge() {
        return backDropLarge;
    }

    public int getRunTime() {
        return runTime;
    }

    public double getRating() {
        return rating;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public ArrayList<String> getProductionCompanies() {
        return productionCompanies;
    }

    public ArrayList<Cast> getCast() {
        return  cast;
    }

    public ArrayList<Crew> getCrew() {
        return crew;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    /**
     * Sets the poster path urls.
     * @param posterPath String
     * The poster url path
     */
    private void setPosters(String posterPath) {
        posterSmall = TMDB_IMG_BASE_URL + "w200" + posterPath;
        posterLarge = TMDB_IMG_BASE_URL + "w500" + posterPath;
    }

    /**
     * Sets the backdrop urls.
     * @param backDropPath String
     * The backdrop url path
     */
    private void setBackDrops(String backDropPath) {
        backDropLarge = TMDB_IMG_BASE_URL + "w500" + backDropPath;
    }

    /**
     * Sets the trailer urls.
     * @param trailerId String
     * The YouTube movie id
     */
    private void setTrailerUrl(String trailerId) {
        trailerUrl =  YT_MOVIE_BASE_URL + "?v=" + trailerId;
    }
}


