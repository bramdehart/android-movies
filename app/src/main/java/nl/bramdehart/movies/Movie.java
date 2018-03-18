package nl.bramdehart.movies;

/**
 * Created by Bram on 17/03/2018.
 */

public class Movie {

    private String title;
    private String category;
    private String description;
    private int poster;

    public Movie() {
    }

    public Movie(String title, String category, String description, int poster) {
        this.title = title;
        this.category = category;
        this.description = description;
        this.poster = poster;
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

    public int getPoster() {
        return poster;
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

    public void setPoster(int poster) {
        this.poster = poster;
    }
}


