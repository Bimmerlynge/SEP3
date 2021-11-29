package server.networking;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.*;
import server.DAO.ISongDAO;
import server.DAO.SongDAO;
import shared.Song;

import java.lang.instrument.Instrumentation;
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

  @PostMapping("/songss")
  public synchronized void postAllSongs(@RequestBody ArrayList<Song> songs)
  {
    System.out.println("Getting post request");
    ISongDAO songDAO = new SongDAO();

    songDAO.postAllSongs(songs);

  }

  @PostMapping("/song")
  public void postSong(@RequestBody Song newSong)
  {
    System.out.println("Getting post request on " + newSong.getTitle());
    ISongDAO songDAO = new SongDAO();

    songDAO.addNewSong(newSong);
  }

  @GetMapping("/songs/{songId}")
  public synchronized String getSongWithMP3(@PathVariable int songId) {
    Song song = songDAO.getSongWithMP3(songId);

    return new Gson().toJson(song);
  }

}
