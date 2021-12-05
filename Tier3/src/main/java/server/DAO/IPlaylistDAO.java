package server.DAO;

import shared.Playlist;
import shared.Song;
import shared.User;

import java.util.ArrayList;

public interface IPlaylistDAO
{
  ArrayList<Playlist> getAllPlaylistForUser(User user);
  int createNewPlaylist(Playlist playlist) throws Exception;
  void removePlaylistFromId(int playlistId) throws NoSuchFieldException;
  Playlist getPlaylistFromId(int playlistId);


}
