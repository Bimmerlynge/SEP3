package server.networking;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import server.DAO.ArtistDAO;
import server.DAO.IArtistDAO;
import shared.Artist;

import java.util.ArrayList;

/**
 * Controller end point håndterer requests hvad angående artister
 */
@RestController
public class ArtistController {

    private IArtistDAO artistDAO = new ArtistDAO();

    /**
     * @param name Søges på artister med navn der matcher
     * @return Hvis parameter er opfyldt returneres en liste med artister der opfylder kriteriet,
     * ellers liste af alle albums i systemet
     */
    @GetMapping("/artist")
    public ResponseEntity<ArrayList<Artist>> searchForArtists(@RequestParam(required = false) String name) {

        try {
            ArrayList<Artist> artistArrayList = null;
            if (name != null) {
                artistArrayList = artistDAO.searchForArtists(name);

            } else {
                artistArrayList = artistDAO.getAllArtist();
            }
            return ResponseEntity.ok(artistArrayList);
        } catch (Exception | InternalError e){
            return ResponseEntity.internalServerError().build();
        }
    }

}
