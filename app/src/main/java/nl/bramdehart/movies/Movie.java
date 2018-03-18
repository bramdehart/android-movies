package nl.bramdehart.movies;

/**
 * Created by Bram on 17/03/2018.
 */

public class Movie {

    private String Title;
    private String Category;
    private String Description;
    private int Poster;

    public Movie() {
    }

    public Movie(String title, String category, String description, int poster) {
        Title = title;
        Category = category;
        Description = description;
        Poster = poster;
    }

    public String getTitle() {
        return Title;
    }

    public String getCategory() {
        return Category;
    }

    public String getDescription() {
        return Description;
    }

    public int getPoster() {
        return Poster;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setPoster(int poster) {
        Poster = poster;
    }
}


