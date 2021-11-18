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
  

//  @GetMapping("/songsByFilter")
//  public synchronized String getSongsByFilter(){
//    ArrayList<Song> songs = songDAO.getSongsByFilter();
//    return new Gson().toJson(songs);
//  }


  @GetMapping("/songs/{songId}")
  public synchronized String getSongWithMP3(@PathVariable int songId) {
    Song song = songDAO.getSongWithMP3(songId);
    System.out.println(song.getMp3().length);
    System.out.println(new Gson().toJson(song).length());
    return new Gson().toJson(song);
  }

  @GetMapping("/song")
  public synchronized String getAllSongsWithArtists() {
    ArrayList<Song> songs = songDAO.getAllSongsWithArtists();
    return new Gson().toJson(songs);
  }
}
