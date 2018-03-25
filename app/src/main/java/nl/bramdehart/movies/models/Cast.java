package nl.bramdehart.movies.models;

/**
 * Created by Bram on 21/03/2018.
 */

/**
 * Represents a single cast member of a movie.
 */
public class Cast extends Person {
    private String character;

    public Cast() {
    }

    /**
     * Initialize a cast member.
     *
     * @param name the cast name
     * @param character the cast character
     * @param profilePath the cast poster url
     */
    public Cast(String name, String character, String profilePath) {
        this.name = name;
        this.character = character;
        this.profilePath = profilePath;
        setProfileUrl(profilePath);
    }

    public String getCharacter() {
        return character;
    }
}
