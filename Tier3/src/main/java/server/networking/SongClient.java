package server.networking;

import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;
import org.springframework.web.client.RestTemplate;
import shared.Artist;
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
    ArrayList<Song> songList = getPlaylistSongs();

    for (Song song: songList) {
      System.out.println(song.getTitle());
    }
  }

  private ArrayList<Song> getPlaylistSongs()
  {
    Gson gson = new Gson();
    Type type = new TypeToken<ArrayList<Song>> (){}.getType();
    String json = rest.getForObject(ROOT + "playlist/1", String.class);
    return gson.fromJson(json, type);
  }

  private String getMessage(){
    Gson gson = new Gson();
    String json = rest.getForObject(ROOT + "message", String.class);
    return gson.fromJson(json, String.class);
  }

  private ArrayList<Song> getAllSongs()
  {
    Gson gson = new Gson();
    Type type = new TypeToken<ArrayList<Song>> (){}.getType();
    String json = rest.getForObject(ROOT + "songs", String.class);
    return gson.fromJson(json, type);
  }

  private Song getSong(){
    Gson gson = new Gson();
    String json = rest.getForObject(ROOT + "song/8", String.class);
    return gson.fromJson(json, Song.class);
  }

  private ArrayList<Song> getAllSongsWithArtists() {
    Gson gson = new Gson();
    Type type = new TypeToken<ArrayList<Song>> (){}.getType();
    String json = rest.getForObject(ROOT + "song", String.class);
    return gson.fromJson(json, type);
  }
}