package server.networking;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.*;
import server.DAO.ISongDAO;
import server.DAO.SongDAO;
import shared.ISong;
import shared.Song;

import java.sql.Date;
import java.util.ArrayList;

@RestController
public class SongController
{
  ISongDAO songDAO = new SongDAO();

  @GetMapping("/song")
  public synchronized String getAllSongs() {
    ArrayList<ISong> songs = songDAO.getAllSongs();
    String json = new Gson().toJson(songs);
    return json;
  }

  @GetMapping("/message")
  public synchronized String getMessage() {
    String message = "Hello Solaiman";
    String json = new Gson().toJson(message);
    return json;
  }

  @GetMapping("/song/{songId}")
  public synchronized String getSong(@PathVariable int songId) {
    ISong song = new Song(songId, "Somewhere", "111sygsang", 5, new Date(2));
    String json = new Gson().toJson(song);
    return json;
  }
}
