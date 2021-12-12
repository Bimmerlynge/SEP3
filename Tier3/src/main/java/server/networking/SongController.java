package server.networking;
import com.google.gson.Gson;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.DAO.ISongDAO;
import server.DAO.SongDAO;
import shared.Song;
import java.net.URI;
import java.util.ArrayList;

@RestController
public class SongController {
    ISongDAO songDAO = new SongDAO();

    @GetMapping("/song")
    public ResponseEntity<?> getAllSongs(@RequestParam(required = false) Integer songId) {
        try {

            if (songId != null){
                Song songById = songDAO.getSongById(songId);
                String songAsJson = new Gson().toJson(songById);
                return ResponseEntity.ok(songAsJson);
            }
            ArrayList<Song> songs = songDAO.getAllSongs();
            String songsAsJson = new Gson().toJson(songs);
            return ResponseEntity.ok(songsAsJson);

        } catch (Exception | InternalError e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }

    }

    @PostMapping("/song")
    public ResponseEntity<URI> postSong(@RequestBody Song newSong) {
        try {
            int newSongId = songDAO.addNewSong(newSong);
            URI uriToFindNewSong = new URI("http://localhost:8080/song?songId=" + newSongId);

            return ResponseEntity.created(uriToFindNewSong).build();
        } catch (Exception | InternalError e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/song/{songId}")
    public ResponseEntity<?> deleteSongFromId(@PathVariable int songId) {
        try{
            songDAO.removeSongFromId(songId);
            return ResponseEntity.ok().build();
        } catch (NoSuchFieldException e){
            return ResponseEntity.notFound().build();
        } catch (Exception | InternalError e){
         return ResponseEntity.internalServerError().build();
        }

    }

}
