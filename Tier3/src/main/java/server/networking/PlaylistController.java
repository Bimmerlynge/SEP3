package server.networking;

import com.google.gson.Gson;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;
import server.DAO.IPlaylistDAO;
import server.DAO.IUserDAO;
import server.DAO.PlaylistDAO;
import server.DAO.UserDAO;
import shared.Playlist;
import shared.Song;
import shared.User;

import java.util.ArrayList;

@RestController
public class PlaylistController {
    IPlaylistDAO playlistDAO = new PlaylistDAO();
    IUserDAO userDao = new UserDAO();


    @PostMapping("/playlist/")
    public synchronized void createNewPlaylistAsync(@RequestBody Playlist playlist) {
        System.out.println("Getting post request on " + playlist.getTitle());
        System.out.println("Post from: " + playlist.getUser().getUsername());

        playlistDAO.createNewPlaylist(playlist);
    }

    @GetMapping("/playlist/{username}")
    public String getPlaylistsForUser(@PathVariable String username) {
        User user = userDao.getUser(username);
        ArrayList<Playlist> playlists = playlistDAO.getAllPlaylistForUser(user);

        return new Gson().toJson(playlists);
    }

    @GetMapping("/playlistSongs/{playlistId}")
    public ResponseEntity<Playlist> getPlaylistFromId (@PathVariable int playlistId) {

        Playlist playlist = playlistDAO.getPlaylistFromId(playlistId);
        return ResponseEntity.ok(playlist);
    }

    @DeleteMapping("/playlist/{playlistId}")
    public void removePlaylistFromId(@PathVariable int playlistId){
        playlistDAO.removePlaylistFromId(playlistId);
    }


}
