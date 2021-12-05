package server.networking;

import com.google.gson.Gson;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import server.DAO.ArtistDAO;
import server.DAO.IArtistDAO;
import shared.Artist;

import java.util.ArrayList;

@RestController

public class ArtistController {

    private IArtistDAO artistDAO = new ArtistDAO();

    @GetMapping("/artist")
    public ResponseEntity searchForArtists(@RequestParam(required = false) String name) {

        try {
            ArrayList<Artist> artistArrayList = null;
            if (name != null) {
                artistArrayList = artistDAO.searchForArtists(name);

            } else {
                artistArrayList = artistDAO.getAllArtist();
            }
            String artistAsJons = new Gson().toJson(artistArrayList);
            return ResponseEntity.ok(artistAsJons);
        } catch (Exception | InternalError e){
            return ResponseEntity.internalServerError().build();
        }
    }

}
