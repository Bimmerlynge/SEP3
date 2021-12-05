package server.networking;

import com.google.gson.Gson;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.DAO.IPlaylistDAO;
import server.DAO.IUserDAO;
import server.DAO.PlaylistDAO;
import server.DAO.UserDAO;
import shared.Playlist;
import shared.User;

import java.net.URI;
import java.util.ArrayList;

@RestController
public class PlaylistController {
    IPlaylistDAO playlistDAO = new PlaylistDAO();
    IUserDAO userDao = new UserDAO();


    @PostMapping("/playlist")
    public ResponseEntity createNewPlaylistAsync(@RequestBody Playlist playlist) {

        try {
            int playlistId = playlistDAO.createNewPlaylist(playlist);
            URI uriToPlaylist = new URI("http://localhost:8080/playlist/" + playlistId);
            return ResponseEntity.created(uriToPlaylist).build();
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

    }

    /*
    Denne metode returnere en liste af playlister hvis man søger på username, men kun 1 playlist hvis man søger på Id
     */
    @GetMapping("/playlist")
    public ResponseEntity getPlaylistsFormUserOrId(@RequestParam(required = false) String username, @RequestParam(required = false) Integer playlistId) {

        if (username != null && playlistId != null) return ResponseEntity.badRequest().build();

        if (username != null){
            User user = userDao.getUser(username);
            ArrayList<Playlist> playlists = playlistDAO.getAllPlaylistForUser(user);
            String playlistsAsJons = new Gson().toJson(playlists);
            return ResponseEntity.ok(playlistsAsJons);

        } else if (playlistId != null){
            Playlist playlist = playlistDAO.getPlaylistFromId(playlistId);
            String playlistAsJson = new Gson().toJson(playlist);
            return ResponseEntity.ok(playlistAsJson);
        }

        return ResponseEntity.badRequest().build();

    }


    @DeleteMapping("/playlist/{playlistId}")
    public ResponseEntity removePlaylistFromId(@PathVariable int playlistId){
        try {
            playlistDAO.removePlaylistFromId(playlistId);
            return ResponseEntity.ok().build();

        } catch (NoSuchFieldException e) {
            return ResponseEntity.badRequest().build();

        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }


}
