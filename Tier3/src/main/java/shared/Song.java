package shared;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Song implements Serializable
{

    private int id;
    private String title;
    private int duration;
    private int releaseYear;
    private ArrayList<Artist> artists;
    private Album album;
    private String songPath;

    public ArrayList<Artist> getArtists()
    {
        return artists;
    }

    public void setArtists(ArrayList<Artist> artists)
    {
        this.artists = artists;
    }

    public void addArtist(Artist artist){
        artists.add(artist);
    }

    public Song(int id, String title, int duration, int releaseYear, Album album, String songPath) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.releaseYear = releaseYear;
        this.album = album;
        this.songPath = songPath;
        artists = new ArrayList<>();
    }

    public String getSongPath()
    {
        return songPath;
    }

    public void setSongPath(String songPath)
    {
        this.songPath = songPath;
    }



    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Song ))
        {
            return false;
        }
        Song song = (Song) o;
        return id == song.id;
    }


    public Album getAlbum()
    {
        return album;
    }

    public void setAlbum(Album album)
    {
        this.album = album;
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

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseDate(int releaseYear) {
        this.releaseYear = releaseYear;
    }






}
