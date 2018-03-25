package nl.bramdehart.movies.models;

/**
 * Created by Bram on 21/03/2018.
 */

public class Crew {

    private String name;
    private String job;
    private String profilePath;
    private String profileUrl;

    private final static String TMDB_IMG_BASE_URL = "http://image.tmdb.org/t/p/";

    public Crew() {
    }

    public Crew(String name, String job, String profilePath) {
        this.name = name;
        this.job = job;
        this.profilePath = profilePath;
        setProfileUrl(profilePath);
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
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
