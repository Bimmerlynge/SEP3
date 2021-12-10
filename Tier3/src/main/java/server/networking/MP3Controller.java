package server.networking;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.DAO.IMP3DAO;
import server.DAO.ISongDAO;
import server.DAO.MP3DAO;
import server.DAO.SongDAO;
import shared.Mp3;
import shared.Song;
import java.net.URI;

@RestController
public class MP3Controller
{

  private IMP3DAO mp3DAO = new MP3DAO();
  private ISongDAO songDAO = new SongDAO();





  @GetMapping("/mp3")
  public ResponseEntity<?> getSongData(@RequestParam(required = false) Integer songId,
                                            @RequestParam(required = false) String pathToMp3)
  {
    try
    {
      if (songId != null){
        Song song = songDAO.getSongById(songId);
        pathToMp3 = song.getMp3();
      }
      byte[] mp3 = mp3DAO.getMp3(pathToMp3);
      return ResponseEntity.ok(mp3);
    }
    catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }

  }

  @PostMapping("/mp3")
  public ResponseEntity<?> uploadMp3(@RequestBody Mp3 song){
    try
    {
      mp3DAO.uploadMp3(song);
      URI uriToThisMp3 = new URI("http://localhost:8080/mp3?pathToMp3=" + song.getPath());
      return ResponseEntity.created(uriToThisMp3).build();
    }
    catch (Exception e){
      return ResponseEntity.badRequest().build();
    }

  }





}
