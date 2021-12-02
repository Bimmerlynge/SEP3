package server.networking;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.*;
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

        playlistDAO.createNewPlaylist(playlist);
    }

    @GetMapping("/playlist/{username}")
    public String getPlaylistsForUser(@PathVariable String username) {
        User user = userDao.getUser(username);
        ArrayList<Playlist> playlists = playlistDAO.getAllPlaylistForUser(user);

        return new Gson().toJson(playlists);
    }

    @GetMapping("/playlistSongs/{playlistId}")
    public String getSongsFromPlaylist(@PathVariable int playlistId) {
        ArrayList<Song> songs = playlistDAO.getSongsInPlaylist(playlistId);

        return new Gson().toJson(songs);
    }

    @DeleteMapping("/playlist/{playlistId}")
    public synchronized void removePlaylistFromId(@PathVariable int playlistId){
        playlistDAO.removePlaylistFromId(playlistId);
    }


}
