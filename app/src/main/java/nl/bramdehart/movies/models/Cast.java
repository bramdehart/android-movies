package nl.bramdehart.movies.models;

/**
 * Created by Bram on 21/03/2018.
 */

public class Cast extends Person {
    private String character;

    public Cast() {
    }

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
