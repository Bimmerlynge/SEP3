package server.networking;

import org.springframework.web.bind.annotation.*;
import server.DAO.IMP3DAO;
import server.DAO.ISongDAO;
import server.DAO.MP3DAO;
import server.DAO.SongDAO;
import shared.Song;

import java.util.ArrayList;

@RestController
public class MP3Controller
{
  ArrayList<byte[]> MP3s = new ArrayList<>();

  @GetMapping("/mp3/{songNo}")
  public synchronized byte[] getAllMP3(@PathVariable int songNo) {
    IMP3DAO MP3DAO = new MP3DAO();
    if (MP3s.isEmpty()) {
      MP3s = MP3DAO.getAllMP3();
    }

    //(SKAL FIXES) Kaster en IndexOutOfBoundsException - det er ikke optimailt, men virker som det skal
    byte[] byteToReturn = MP3s.get(songNo);
    return byteToReturn;
    //return new Gson().toJson(MP3s);
  }


}
