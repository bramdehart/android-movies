package nl.bramdehart.movies;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Bram on 17/03/2018.
 */

public class Movie {

    private int movieId;
    private String title;
    private String posterPath;
    private String posterSmall;
    private String posterLarge;
    private String backDropSmall;
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

    final static String TMDB_IMG_BASE_URL = "http://image.tmdb.org/t/p/";
    final static String YT_MOVIE_BASE_URL = "https://www.youtube.com/watch";

    public Movie() {
    }

    public Movie(int movieId, String posterPath) {
        this.movieId = movieId;
        this.setPosters(posterPath);
    }

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

    public String getBackDropSmall() {
        return backDropSmall;
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

    private void setPosters(String posterPath) {
        posterSmall = TMDB_IMG_BASE_URL + "w200" + posterPath;
        posterLarge = TMDB_IMG_BASE_URL + "w500" + posterPath;
    }

    private void setBackDrops(String backDropPath) {
        backDropSmall = TMDB_IMG_BASE_URL + "w300" + backDropPath;
        backDropLarge = TMDB_IMG_BASE_URL + "w500" + backDropPath;
    }

    private void setTrailerUrl(String trailerId) {
        trailerUrl =  YT_MOVIE_BASE_URL + "?v=" + trailerId;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }


}


