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

  @GetMapping("/song")
  public synchronized String getAllSongs() {
    ArrayList<Song> songs = songDAO.getAllSongs();
    return new Gson().toJson(songs);
  }

  @GetMapping("/message")
  public synchronized String getMessage() {
    String message = "Hello Solaiman";
    return new Gson().toJson(message);
  }

  @GetMapping("/song/{songId}")
  public synchronized String getSong(@PathVariable int songId) {
    Song song = new Song(songId, "Somewhere", "111sygsang", 5, new Date(2));
    return new Gson().toJson(song);
  }
}
