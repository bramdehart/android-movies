package nl.bramdehart.movies.models;

/**
 * Created by Bram on 21/03/2018.
 */

public class Cast {
    private String name;
    private String character;
    private String profilePath;
    private String profileUrl;

    private final static String TMDB_IMG_BASE_URL = "http://image.tmdb.org/t/p/";

    public Cast() {
    }

    public Cast(String name, String character, String profilePath) {
        this.name = name;
        this.character = character;
        this.profilePath = profilePath;
        setProfileUrl(profilePath);
    }

    public String getName() {
        return name;
    }

    public String getCharacter() {
        return character;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    private void setProfileUrl(String profilePath) {
        profileUrl = TMDB_IMG_BASE_URL + "w300" + profilePath;
    }

    public String getProfilePath() {
        return profilePath;
    }
}
