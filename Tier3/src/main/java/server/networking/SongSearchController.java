package server.networking;

import com.google.gson.Gson;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import server.DAO.ISongSearchDAO;
import server.DAO.SongSearchDAO;
import shared.Song;

import java.util.ArrayList;


@RestController

public class SongSearchController {
    ISongSearchDAO songSearchDAO = new SongSearchDAO();


    @GetMapping("/songSearch")
    public ResponseEntity<?> getSongsByFilter(@RequestParam(required = false) String songTitle,
                                           @RequestParam(required = false) String artistName,
                                           @RequestParam(required = false) String albumTitle) {
        if (checkIfMoreThanOneArgument(songTitle, artistName, albumTitle)) {
            ResponseEntity.badRequest().build();
        }
        try {
            ArrayList<Song> songs = null;
            if (songTitle != null) {
                songs = songSearchDAO.getSongsByTitle(songTitle);
            } else if (artistName != null) {
                songs = songSearchDAO.getSongsByArtist(artistName);
            } else if (albumTitle != null) {
                songs = songSearchDAO.getSongsByAlbum(albumTitle);
            } else {
                return ResponseEntity.badRequest().build();
            }

            String songsAsJson = new Gson().toJson(songs);
            return ResponseEntity.ok(songsAsJson);
        } catch (Exception | InternalError e){
            return ResponseEntity.internalServerError().build();
        }

    }

    private boolean checkIfMoreThanOneArgument(String songTitle, String artistName, String albumTitle) {
        boolean checker = false;
        boolean songTitleNull = songTitle != null;
        boolean artistNameNull = artistName != null;
        boolean albumTitleNull = albumTitle != null;

        if (songTitleNull && artistNameNull) {
            checker = true;
        } else if (songTitleNull && albumTitleNull) {
            checker = true;
        } else if (artistNameNull && albumTitleNull) {
            checker = true;
        }
        return checker;
    }


}
