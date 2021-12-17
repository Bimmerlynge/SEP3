package server.DAO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shared.Album;
import shared.Playlist;
import shared.Song;
import shared.User;

import static org.junit.jupiter.api.Assertions.*;

/**
  * For User i denne klasse bliver Admin Admin brugt, dette er en hard coded admin user.
 */
class PlaylistManageDAOTest {


    private IPlaylistManageDAO playlistManageDAO = new PlaylistManageDAO();
    private IPlaylistDAO playlistDAO = new PlaylistDAO();
    private IUserDAO userDAO = new UserDAO();
    private ISongDAO songDAO = new SongDAO();
    private User adminUser;
    private Song validSong;
    private Album validAlbum;


    @BeforeEach
    void setUp(){
        adminUser = userDAO.getUser("Admin");
        validAlbum = new Album(0, "ValidAlbumTitle");
        validSong = new Song(0, "NewSong", 300, 2021, validAlbum, null);
        int songId = songDAO.addNewSong(validSong);
        validSong.setId(songId);
    }

    @Test
    void AddSongToPlaylistSunny() throws Exception {
        Playlist newPlaylist = new Playlist(0, "ValidPlaylist", adminUser);
        int playlistId = playlistDAO.createNewPlaylist(newPlaylist);

        Playlist newPlaylistFromDatabase = playlistDAO.getPlaylistFromId(playlistId);
        playlistManageDAO.addSongToPlayList(playlistId, validSong);

        Playlist newPlaylistButWithSong = playlistDAO.getPlaylistFromId(playlistId);

        assertEquals(newPlaylistFromDatabase.getSongs().size()+1, newPlaylistButWithSong.getSongs().size());
    }

    @Test
    void AddSongToPlaylistNullSong() throws Exception {
        Playlist newPlaylist = new Playlist(0, "ValidPlaylist", adminUser);
        int playlistId = playlistDAO.createNewPlaylist(newPlaylist);

        assertThrows(InternalError.class, () ->playlistManageDAO.addSongToPlayList(playlistId, null));


    }

    @Test
    void AddSongToPlaylistNotValidSong() throws Exception {
        Playlist newPlaylist = new Playlist(0, "ValidPlaylist", adminUser);
        int playlistId = playlistDAO.createNewPlaylist(newPlaylist);
        Song notValidSong = new Song(0, "ThisSongNoId", 2, 2020, null, null);

        assertThrows(InternalError.class, () -> playlistManageDAO.addSongToPlayList(playlistId, notValidSong));
    }

    @Test
    void RemoveSongFromPlaylistSunny() throws Exception {
        Playlist newPlaylist = new Playlist(0, "ValidPlaylist", adminUser);
        int playlistId = playlistDAO.createNewPlaylist(newPlaylist);

        playlistManageDAO.addSongToPlayList(playlistId, validSong);
        Playlist newPlaylistButWithSong = playlistDAO.getPlaylistFromId(playlistId);

        playlistManageDAO.removeSongFromPlaylist(playlistId, validSong.getId());
        Playlist newPlaylistButWithSongRemoved = playlistDAO.getPlaylistFromId(playlistId);

        assertEquals(newPlaylistButWithSong.getSongs().size()-1, newPlaylistButWithSongRemoved.getSongs().size());

    }

    @Test
    void RemoveSongFromPlaylistSongNotInPlaylist() throws Exception {
        Playlist newPlaylist = new Playlist(0, "ValidPlaylist", adminUser);
        int playlistId = playlistDAO.createNewPlaylist(newPlaylist);


        assertThrows(InternalError.class,() -> playlistManageDAO.removeSongFromPlaylist(playlistId, -1));


    }

    @Test
    void RemoveSongFromPlaylistPlaylistNotInDatabase() {

        assertThrows(InternalError.class,() -> playlistManageDAO.removeSongFromPlaylist(-1, validSong.getId()));

    }


}