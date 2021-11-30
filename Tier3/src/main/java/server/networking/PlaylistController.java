package server.networking;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import server.DAO.IPlaylistDAO;
import server.DAO.IUserDAO;
import server.DAO.PlaylistDAO;
import server.DAO.UserDAO;
import shared.Album;
import shared.Playlist;
import shared.Song;
import shared.User;

import java.util.ArrayList;

@RestController
public class PlaylistController
{
  IPlaylistDAO playlistDAO = new PlaylistDAO();
  IUserDAO userDao = new UserDAO();

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
}
