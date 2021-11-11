package server.networking;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.*;
import server.DAO.ISongDAO;
import server.DAO.SongDAO;
import shared.Song;

import java.sql.Date;
import java.util.ArrayList;

@RestController
public class SongController
{
  ISongDAO songDAO = new SongDAO();

  @GetMapping("/songs")
  public synchronized String getAllSongs() {
    ArrayList<Song> songs = songDAO.getAllSongs();
    return new Gson().toJson(songs);
  }
  

//  @GetMapping("/songsByFilter")
//  public synchronized String getSongsByFilter(){
//    ArrayList<Song> songs = songDAO.getSongsByFilter();
//    return new Gson().toJson(songs);
//  }

  @GetMapping("/songs/{type}={parameter}")
  public synchronized String getSongsByFilter(@PathVariable String type, @PathVariable String parameter){
    System.out.println("Type: " + type + ", parameter: " + parameter);
    ArrayList<Song> songs = songDAO.getSongsByFilter(type, parameter);
    System.out.println("Sender list, size: " + songs.size());
    return new Gson().toJson(songs);
  }

  @GetMapping("/message")
  public synchronized String getMessage() {
    String message = "Hello Solaiman";
    return new Gson().toJson(message);
  }

  @GetMapping("/song/{songId}")
  public synchronized String getSong(@PathVariable int songId) {
    System.out.println("Hallo");
    Song song = new Song(songId, "Somewhere", "111sygsang", 5, new Date(2));
    return new Gson().toJson(song);
  }

  @GetMapping("/song")
  public synchronized String getAllSongsWithArtists() {
    ArrayList<Song> songs = songDAO.getAllSongsWithArtists();
    return new Gson().toJson(songs);
  }
}
