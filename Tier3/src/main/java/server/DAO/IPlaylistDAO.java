package server.DAO;

import shared.Playlist;
import shared.Song;
import shared.User;

import java.util.ArrayList;

public interface IPlaylistDAO
{
  ArrayList<Playlist> getAllPlaylistForUser(User user);
  void createNewPlaylist(Playlist playlist);
  void removePlaylistFromId(int playlistId);
  Playlist getPlaylistFromId(int playlistId);


}
