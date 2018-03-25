package nl.bramdehart.movies.models;

/**
 * Created by Bram on 21/03/2018.
 */

public class Crew extends Person {
    private String job;

    public Crew() {
    }

    public Crew(String name, String job, String profilePath) {
        this.name = name;
        this.job = job;
        this.profilePath = profilePath;
        setProfileUrl(profilePath);
    }

    public String getJob() {
        return job;
    }
}
