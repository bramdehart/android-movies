package nl.bramdehart.movies;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Bram on 17/03/2018.
 */

public class Movie {

    private int movieId;
    private String title;
    private String posterSmall;
    private String posterLarge;
    private String backDropSmall;
    private String backDropLarge;
    private int runTime;
    private double rating;
    private String overview;
    private String releaseDate;
    private ArrayList<String> genres;
    private ArrayList<String> productionCompanies;

    final static String TMDB_IMG_BASE_URL = "http://image.tmdb.org/t/p/";

    public Movie() {
    }

    public Movie(int movieId, String posterPath) {
        this.movieId = movieId;
        this.setPosters(posterPath);
    }

    public Movie(String title, String posterPath, String backDropPath, int runTime, double rating, String overview, String releaseDate, ArrayList<String> genres, ArrayList<String> productionCompanies) {
        this.title = title;
        this.runTime = runTime;
        this.rating = rating;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.genres = genres;
        this.productionCompanies = productionCompanies;
        setPosters(posterPath);
        setBackDrops(backDropPath);
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

    public void setPosters(String posterPath) {
        posterSmall = TMDB_IMG_BASE_URL + "w200" + posterPath;
        posterLarge = TMDB_IMG_BASE_URL + "w500" + posterPath;
    }

    public void setBackDrops(String backDropPath) {
        backDropSmall = TMDB_IMG_BASE_URL + "w300" + backDropPath;
        backDropLarge = TMDB_IMG_BASE_URL + "w500" + backDropPath;
    }


}

