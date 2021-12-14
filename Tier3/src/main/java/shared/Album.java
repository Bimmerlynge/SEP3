package shared;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.Serializable;

public class Album implements Serializable
{
  private int id;
  private String title;


  public Album(int id, String title)
  {
    this.id = id;
    this.title = title;
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

  public void setId(int id) {
    this.id = id;
  }

  public void setTitle(String title)
  {
    this.title = title;

  }


}
