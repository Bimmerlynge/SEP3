package server.networking;

import org.springframework.web.bind.annotation.*;
import server.DAO.IPlaylistManageDAO;
import server.DAO.PlaylistManageDAO;
import shared.Song;

@RestController
public class PlaylistManageController
{
  private IPlaylistManageDAO playlistManageDAO = new PlaylistManageDAO();

  @PostMapping("/playlist/{playlistId}/")
  public void addSongToPlaylist(@PathVariable int playlistId, @RequestBody Song song){
    playlistManageDAO.addSongToPlayList(playlistId,song);

  }

  @DeleteMapping("/playlist/{playlistId}/{songId}")
  public void removeSongFromPlaylist(@PathVariable int playlistId, @PathVariable int songId){
    playlistManageDAO.removeSongFromPlaylist(playlistId,songId);
  }
}
