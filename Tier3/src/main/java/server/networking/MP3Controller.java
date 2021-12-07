package server.networking;

import com.google.gson.Gson;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.DAO.IMP3DAO;
import server.DAO.ISongDAO;
import server.DAO.MP3DAO;
import server.DAO.SongDAO;
import shared.Mp3;
import shared.Song;

import java.util.ArrayList;

@RestController
public class MP3Controller
{

  private IMP3DAO mp3DAO = new MP3DAO();





  @GetMapping("/mp3")
  public ResponseEntity<byte[]> getSongData(@RequestParam String songPath)
  {
    byte[] mp3 = mp3DAO.getMp3(songPath);


    return ResponseEntity.ok(mp3);
  }

  @PostMapping("/mp3")
  public ResponseEntity<String> uploadMp3(@RequestBody Mp3 song){
    System.out.println("Trying to upload: " + song.getPath());
    try
    {
      mp3DAO.uploadMp3(song);
      return ResponseEntity.ok().build();
    } catch (Exception e){
      return ResponseEntity.badRequest().build();
    }

  }





}
