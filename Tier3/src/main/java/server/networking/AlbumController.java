package server.networking;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import server.DAO.AlbumDAO;
import server.DAO.IAlbumDAO;
import shared.Album;
import shared.Artist;

import java.util.ArrayList;

@RestController
public class AlbumController {

    private IAlbumDAO albumDAO = new AlbumDAO();

    @GetMapping("/album/{title}")
    public String searchForArtists(@PathVariable String title) {
        ArrayList<Album> albumArrayList = albumDAO.searchForAlbums(title);

        return new Gson().toJson(albumArrayList);
    }
}
