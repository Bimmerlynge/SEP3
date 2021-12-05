package server.networking;

import com.google.gson.Gson;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import server.DAO.AlbumDAO;
import server.DAO.IAlbumDAO;
import shared.Album;
import shared.Artist;

import java.util.ArrayList;

@RestController
public class AlbumController {

    private IAlbumDAO albumDAO = new AlbumDAO();

    @GetMapping("/album")
    public ResponseEntity searchForAlbums(@RequestParam(required = false) String title) {
        ArrayList<Album> albumArrayList = null;
        if (title != null){
            albumArrayList = albumDAO.searchForAlbums(title);
        } else {
            albumArrayList = albumDAO.getAllAlbums();
        }
        String albumListAsJson = new Gson().toJson(albumArrayList);
        return ResponseEntity.ok(albumListAsJson);
    }


}
