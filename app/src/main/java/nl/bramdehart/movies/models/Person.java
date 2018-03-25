package nl.bramdehart.movies.models;

/**
 * Created by bram on 25/03/18.
 */

public class Person {

    public String name;
    public String profilePath;
    public String profileUrl;
    public final static String TMDB_IMG_BASE_URL = "http://image.tmdb.org/t/p/";

    public Person() {

    }

    public String getName() {
        return name;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profilePath) {
        profileUrl = TMDB_IMG_BASE_URL + "w300" + profilePath;
    }
}
