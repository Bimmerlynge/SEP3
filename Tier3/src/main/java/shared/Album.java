package shared;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Album
{
  private int id;
  private String title;
  private int duration;


  public Album(int id, String title, int duration)
  {
    this.id = id;
    this.title = title;
    this.duration = duration;
  }

  public String toString(){
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    return gson.toJson(this);
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

  public void setDuration(int duration)
  {
    this.duration = duration;
  }

  public int getDuration()
  {
    return duration;
  }


}
