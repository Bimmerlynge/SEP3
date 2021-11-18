package shared;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Objects;

public class Song {

    private int id;
    private String title;
    private int duration;
    private int releaseYear;
    private ArrayList<Artist> artists;
    private ArrayList<Album> albums;
    private Album albumProperty;
    private byte[] mp3;


    public Song(int id, String title, int duration, int releaseYear) {
        this.id = id;
        this.url = url;
        this.title = title;
        this.duration = duration;
        this.releaseYear = releaseYear;
        artists = new ArrayList<>();
    }

    public byte[] getMp3()
    {
        return mp3;
    }

    public void setMp3(byte[] mp3)
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

    public ArrayList<Album> getAlbums()
    public String getArtistName() {
        return artists.get(0).getArtistName();
    }

    public Album getAlbumProperty()
    {
        return albumProperty;
    }

    public void setAlbums(Album album)
    {
        this.albumProperty = album;
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
