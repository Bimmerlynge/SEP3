package server.networking;

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

    /**
     * Kun 1 parameter udfyldt pr request.
     * @param songTitle Søge efter sange hvor titlen er songTitle
     * @param artistName Søge efter Sange lavet af artist med navn artistName
     * @param albumTitle Søge efter Sange på et album med Title albumTitle
     * @return Arraylist<Song> hvis request går som forventet, eller returneres relevante fejl koder
     */
    @GetMapping("/songSearch")
    public ResponseEntity<ArrayList<Song>> getSongsByFilter(@RequestParam(required = false) String songTitle,
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

            return ResponseEntity.ok(songs);
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
