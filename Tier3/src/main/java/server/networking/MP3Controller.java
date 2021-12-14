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

/**
 * Controller end point, som håndtere requests hvad angår Mp3 filer.
 * Det er her de store datamængder bliver sendt
 */
@RestController
public class MP3Controller
{
  private IMP3DAO mp3DAO = new MP3DAO();
  private ISongDAO songDAO = new SongDAO();

  @GetMapping("/mp3")
  public ResponseEntity<byte[]> getSongData(@RequestParam Integer songId)
  {
    try
    {
      Song song = songDAO.getSongById(songId);
      byte[] mp3 = mp3DAO.getMp3(song.getSongPath());
      return ResponseEntity.ok(mp3);
    }
    catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }

  }

  @PostMapping("/mp3/{songId}")
  public ResponseEntity<URI> uploadMp3(@PathVariable int songId, @RequestBody Mp3 song){
    try
    {
      Song songWithPath = songDAO.getSongById(songId);
      song.setPath(songWithPath.getSongPath());
      mp3DAO.uploadMp3(song);
      URI uriToThisMp3 = new URI("http://localhost:8080/mp3?songId=" + songWithPath.getId());
      return ResponseEntity.created(uriToThisMp3).build();
    }
    catch (Exception e){
      e.printStackTrace();
      return ResponseEntity.badRequest().build();
    }

  }





}
