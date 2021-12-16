package server.networking;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import server.DAO.AlbumDAO;
import server.DAO.IAlbumDAO;
import shared.Album;
import java.util.ArrayList;

/**
 * Controller end point håndtere requests hvad angår Albums
 */
@RestController
public class AlbumController {

    private IAlbumDAO albumDAO = new AlbumDAO();

    /**
     *
     * @param title Søges på album med title der matcher
     * @return Hvis parameter opfyldt liste med album der opfylder kriteriet, ellers liste af alle albums i systemet
     */
    @GetMapping("/album")
    public ResponseEntity<ArrayList<Album>> searchForAlbums(@RequestParam(required = false) String title) {
        try {
            ArrayList<Album> albumArrayList = null;
            if (title != null) {
                albumArrayList = albumDAO.searchForAlbums(title);
            } else {
                albumArrayList = albumDAO.getAllAlbums();
            }
            return ResponseEntity.ok(albumArrayList);
        } catch (Exception | InternalError e){
            return ResponseEntity.internalServerError().build();
        }
    }


}
