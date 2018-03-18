package nl.bramdehart.movies;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Bram on 17/03/2018.
 */

public class Movie {

    private String title;
    private String category;
    private String description;
    private String posterSmall;
    private String posterLarge;
    private String backDropSmall;
    private String backDropLarge;

    final static String TMDB_IMG_BASE_URL = "http://image.tmdb.org/t/p/";

    public Movie() {
    }

    public Movie(String title, String category, String description, String posterPath, String backDropPath) {
        this.title = title;
        this.category = category;
        this.description = description;
        this.setPosters(posterPath);
        this.setBackDrops(backDropPath);
    }

    public Movie(String title, String posterPath) {
        this.title = title;
        this.setPosters(posterPath);
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public void setPosters(String posterPath) {
        posterSmall = TMDB_IMG_BASE_URL + "w200" + posterPath;
        posterLarge = TMDB_IMG_BASE_URL + "w500" + posterPath;
    }

    public void setBackDrops(String backDropPath) {
        posterSmall = TMDB_IMG_BASE_URL + "w200" + backDropPath;
        posterLarge = TMDB_IMG_BASE_URL + "w500" + backDropPath;
    }


}


