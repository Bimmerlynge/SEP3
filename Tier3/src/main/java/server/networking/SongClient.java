package server.networking;

import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;
import org.springframework.web.client.RestTemplate;
import shared.ISong;
import shared.Song;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SongClient
{
  private static final String ROOT = "http://localhost:8080/";
  private RestTemplate rest = new RestTemplate();

  public static void main(String[] args)
  {
    new SongClient().run();
  }

  private void run()
  {
    System.out.println(getMessage());
    ISong song1 = getSong();
    System.out.println(song1.getTitle());
    ArrayList<ISong> songList = getAllSongs();

    for (ISong song: songList) {
      System.out.println(song.getTitle());
    }
  }

  private String getMessage(){
    Gson gson = new Gson();
    String json = rest.getForObject(ROOT + "message", String.class);
    return gson.fromJson(json, String.class);
  }

  private ArrayList<ISong> getAllSongs()
  {
    Gson gson = new Gson();
    Type type = new TypeToken<ArrayList<Song>> (){}.getType();
    String json = rest.getForObject(ROOT + "song", String.class);
    ArrayList<ISong> songs = gson.fromJson(json, type);
    return songs;
  }

  private ISong getSong(){
    Gson gson = new Gson();
    String json = rest.getForObject(ROOT + "song/8", String.class);
    ISong song = gson.fromJson(json, Song.class);
    return song;
  }
}
