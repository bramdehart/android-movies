package nl.bramdehart.movies.models;

/**
 * Created by Bram on 21/03/2018.
 */

/**
 * Initialize a single crew member of a movie.
 */
public class Crew extends Person {
    private String job;

    public Crew() {
    }

    /**
     * Initialize a crew member.
     * @param name the crew name
     * @param job the crew job
     * @param profilePath the crew poster url
     */
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
