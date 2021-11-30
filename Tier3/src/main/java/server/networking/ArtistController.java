package server.networking;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import server.DAO.ArtistDAO;
import server.DAO.IArtistDAO;
import shared.Artist;

import java.util.ArrayList;

@RestController

public class ArtistController {

    private IArtistDAO artistDAO = new ArtistDAO();

    @GetMapping("/artist/{name}")
    public String searchForArtists(@PathVariable String name) {
        ArrayList<Artist> artistArrayList = artistDAO.searchForArtists(name);

        return new Gson().toJson(artistArrayList);
    }

    @GetMapping("/artist")
    public String getAllArtist() {
        ArrayList<Artist> artistArrayList = artistDAO.getAllArtist();

        return new Gson().toJson(artistArrayList);
    }
}
