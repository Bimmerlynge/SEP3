package shared;

import java.sql.Date;

public interface ISong {

  String toString();
  int getId();
  void setId(int id);
  String getUrl();
  void setUrl(String url);
  String getTitle();
  void setTitle(String title);
  int getDuration();
  void setDuration(int duration);
  Date getReleaseDate();
  void setReleaseDate(Date releaseDate);

}
