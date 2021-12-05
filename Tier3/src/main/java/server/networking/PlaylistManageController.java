package server.networking;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.DAO.IPlaylistManageDAO;
import server.DAO.PlaylistManageDAO;
import shared.Song;

@RestController
public class PlaylistManageController {
    private IPlaylistManageDAO playlistManageDAO = new PlaylistManageDAO();

    @PostMapping("/playlistManage/{playlistId}/")
    public ResponseEntity addSongToPlaylist(@PathVariable int playlistId, @RequestBody Song song) {
        try {
            playlistManageDAO.addSongToPlayList(playlistId, song);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }

    @DeleteMapping("/playlistManage/{playlistId}/{songId}")
    public ResponseEntity removeSongFromPlaylist(@PathVariable int playlistId, @PathVariable int songId) {
        try {
            playlistManageDAO.removeSongFromPlaylist(playlistId, songId);
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
