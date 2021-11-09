package shared;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Date;
import java.util.ArrayList;

public class Album
{
  private int albumId;
  private String title;
  private int duration;
  private Date date;
  private ArrayList<Artist> artists;

  public Album(int albumId, String title, int duration, Date date)
  {
    this.albumId = albumId;
    this.title = title;
    this.duration = duration;
    this.date = date;
    artists = new ArrayList<>();
  }

  public String toString(){
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    return gson.toJson(this);
  }

  public void addArtist(Artist artist){
    artists.add(artist);
  }

  public int getAlbumId()
  {
    return albumId;
  }


  public String getTitle()
  {
    return title;
  }


  public int getDuration()
  {
    return duration;
  }


  public Date getDate()
  {
    return date;
  }



}