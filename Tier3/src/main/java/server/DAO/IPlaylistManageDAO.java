package server.DAO;

import shared.Song;

public interface IPlaylistManageDAO
{
  void addSongToPlayList(int playlistId, Song song);
  void removeSongFromPlaylist(int playlistId, int songId);

}
