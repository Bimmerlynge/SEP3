package server.networking;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.*;
import server.DAO.ISongDAO;
import server.DAO.SongDAO;
import shared.Song;
import java.util.ArrayList;

@RestController
public class SongController
{
  ISongDAO songDAO = new SongDAO();

  @GetMapping("/songs")
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
  public synchronized void postSong(@RequestBody Song newSong)
  {
    System.out.println("Getting post request on " + newSong.getTitle());
    ISongDAO songDAO = new SongDAO();

    songDAO.addNewSong(newSong);
  }

  @GetMapping("/songs/{songId}")
  public byte[] getSongWithMP3(@PathVariable int songId)
  {
    Song song = songDAO.getSongWithMP3(songId);
    System.out.println("Length of byte array: " + song.getMp3().length);
    String songJson = new Gson().toJson(song.getMp3());
    System.out.println("Length of Json mp3: " + songJson.length());
    return song.getMp3();
  }

  @DeleteMapping("/song/{songId}")
  public synchronized void deleteSongFromId(@PathVariable int songId){
    songDAO.removeSongFromId(songId);
  }

}
