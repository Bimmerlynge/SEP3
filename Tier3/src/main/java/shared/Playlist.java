package shared;

import java.util.ArrayList;

public class Playlist
{
  private int id;
  private String title;
  private User user;
  private ArrayList<Song> songs;

    public Playlist(int id, String title, User user)
    {
        this.id = id;
        this.title = title;
        this.user = user;
        songs = new ArrayList<>();
    }

    public int getId()
    {
        return id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public ArrayList<Song> getSongs()
    {
        return songs;
    }

    public void setSongs(ArrayList<Song> songs)
    {
        this.songs = songs;
    }

    public void addSong(Song song){
        songs.add(song);
    }
}
