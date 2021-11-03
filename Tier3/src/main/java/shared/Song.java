package shared;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Date;
import java.util.ArrayList;

public class Song {

    private int id;
    private String url;
    private String title;
    private int duration;
    private Date releaseDate;
    private ArrayList<Artist> artists;


    public Song(int id, String url, String title, int duration, Date releaseDate) {
        this.id = id;
        this.url = url;
        this.title = title;
        this.duration = duration;
        this.releaseDate = releaseDate;
        artists = new ArrayList<>();
    }

    public String toString(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        return gson.toJson(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void addArtist(Artist artist) {
        artists.add(artist);
    }

    public void setArtists(ArrayList<Artist> artists)
    {
        this.artists = artists;
    }

    public ArrayList<Artist> getArtists()
    {
        return artists;
    }
}
