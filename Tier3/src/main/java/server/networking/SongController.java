package server.networking;

import com.google.gson.Gson;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.DAO.IMP3DAO;
import server.DAO.ISongDAO;
import server.DAO.MP3DAO;
import server.DAO.SongDAO;
import shared.Song;
import java.util.ArrayList;

@RestController
public class SongController
{
  ISongDAO songDAO = new SongDAO();


  @GetMapping("/song")
  public String getAllSongs() {
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
  public synchronized ResponseEntity postSong(@RequestBody Song newSong)
  {
    System.out.println("Getting post request on " + newSong.getTitle());
    ISongDAO songDAO = new SongDAO();
    try
    {
      Song song = songDAO.addNewSong(newSong);
      String songAsJson = new Gson().toJson(song);
      return ResponseEntity.ok(songAsJson);
    }catch (Exception e){
      return ResponseEntity.internalServerError().build();
    }
  }



  @DeleteMapping("/song/{songId}")
  public synchronized void deleteSongFromId(@PathVariable int songId){
    songDAO.removeSongFromId(songId);
  }

}
