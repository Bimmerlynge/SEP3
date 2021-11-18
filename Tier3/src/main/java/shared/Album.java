package shared;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Date;
import java.util.ArrayList;

public class Album
{
  private int albumId;
  private String albumTitle;
  private int duration;


  public Album(int albumId, String title, int duration)
  {
    this.albumId = albumId;
    this.albumTitle = title;
    this.duration = duration;
  }

  public String toString(){
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    return gson.toJson(this);
  }


  public int getAlbumId()
  {
    return albumId;
  }


  public String getTitle()
  {
    return albumTitle;
  }

  public void setAlbumTitle(String albumTitle)
  {
    this.albumTitle = albumTitle;
  }

  public void setDuration(int duration)
  {
    this.duration = duration;
  }

  public int getDuration()
  {
    return duration;
  }


}
