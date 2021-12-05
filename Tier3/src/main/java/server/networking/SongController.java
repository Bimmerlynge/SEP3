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
  public ResponseEntity getAllSongs() {
    ArrayList<Song> songs = songDAO.getAllSongs();

    String songsAsJson = new Gson().toJson(songs);
    return ResponseEntity.ok(songsAsJson);
  }

  @PostMapping("/song")
  public ResponseEntity postSong(@RequestBody Song newSong)
  {
    try
    {
      ISongDAO songDAO = new SongDAO();

      Song song = songDAO.addNewSong(newSong);
      String songAsJson = new Gson().toJson(song);
      return ResponseEntity.ok(songAsJson);
    }catch (Exception e){
      return ResponseEntity.internalServerError().build();
    }
  }

  @DeleteMapping("/song/{songId}")
  public ResponseEntity deleteSongFromId(@PathVariable int songId){
    songDAO.removeSongFromId(songId);
    return ResponseEntity.ok().build();
  }

}
