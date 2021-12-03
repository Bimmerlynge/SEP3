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
    private String mp3;

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

    public Song(int id, String title, int duration, int releaseYear, Album album, String mp3) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.releaseYear = releaseYear;
        this.album = album;
        this.mp3 = mp3;
        artists = new ArrayList<>();
    }

    public String getMp3()
    {
        return mp3;
    }

    public void setMp3(String mp3)
    {
        this.mp3 = mp3;
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

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

//    public Album[] getAlbums()
//    {
//        return albums;
//    }
//
//    public void setAlbums(Album[] albums)
//    {
//        this.albums = albums;
//    }

    public Album getAlbumProperty()
    {
        return album;
    }

    public void setAlbums(Album album)
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
