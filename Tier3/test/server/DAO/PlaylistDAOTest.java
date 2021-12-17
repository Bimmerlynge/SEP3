package server.DAO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shared.Playlist;
import shared.User;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * For User i denne klasse bliver Admin Admin brugt, dette er en hard coded admin user.
 */
class PlaylistDAOTest {

    private IUserDAO userDAO = new UserDAO();
    private IPlaylistDAO playlistDAO = new PlaylistDAO();
    private User adminUser;

    @BeforeEach
    void setUp(){
        adminUser = userDAO.getUser("Admin");
    }
    @Test
    void CreateNewPlaylistSunny() throws Exception {

        Playlist newPlaylist = new Playlist(0, "JammingList", adminUser);

        playlistDAO.createNewPlaylist(newPlaylist);

        ArrayList<Playlist> playlistsForUser = playlistDAO.getAllPlaylistForUser(adminUser);

        for (Playlist playlist : playlistsForUser) {
            if (newPlaylist.getTitle().equals(playlist.getTitle())){
               assertEquals(newPlaylist.getTitle(), playlist.getTitle());

               //Tear down
                playlistDAO.removePlaylistFromId(playlist.getId());
                return;
            }
        }
        fail();
    }

    @Test
    void CreateNewPlaylistEmptyTitle(){
        Playlist newPlaylist = new Playlist(0, "", adminUser);

        assertThrows(IllegalArgumentException.class, () ->playlistDAO.createNewPlaylist(newPlaylist));
    }

    @Test
    void CreateNewPlaylistNullTitle(){
        Playlist newPlaylist = new Playlist(0, null, adminUser);

        assertThrows(IllegalArgumentException.class, () ->playlistDAO.createNewPlaylist(newPlaylist));
    }

    @Test
    void CreateNewPlaylistNullUser(){
        Playlist newPlaylist = new Playlist(0, "ValidPlaylistTitle", null);

        assertThrows(IllegalArgumentException.class, () ->playlistDAO.createNewPlaylist(newPlaylist));
    }

    @Test
    void RemovePlaylistRemovesPlaylistFromUser() throws Exception {

        Playlist newPlaylist = new Playlist(0, "JammingList", adminUser);
        int newPlaylistId = playlistDAO.createNewPlaylist(newPlaylist);
        int countBefore = playlistDAO.getAllPlaylistForUser(adminUser).size();


        playlistDAO.removePlaylistFromId(newPlaylistId);
        int countAfter = playlistDAO.getAllPlaylistForUser(adminUser).size();

        assertEquals(countBefore-1, countAfter);

    }

    @Test
    void RemovePlaylistNotInDatabase(){

        assertThrows(NoSuchFieldException.class, () -> playlistDAO.removePlaylistFromId(-1));
    }



}