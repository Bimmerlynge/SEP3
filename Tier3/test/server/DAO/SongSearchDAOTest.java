package server.DAO;

import org.junit.jupiter.api.Test;
import shared.Song;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SongSearchDAOTest {

    private ISongSearchDAO songSearchDAO = new SongSearchDAO();
    private ISongDAO songDAO = new SongDAO();
    private String notFoundInput = "NotASongTitleOrArtistNameOrAlbumTitleasdfasaj21nf";


    @Test
    void TestIfCorrectSongIsFoundByTitle() {

        ArrayList<Song> listWithEverySongInDatabase = songDAO.getAllSongs();
        String songTest1 = listWithEverySongInDatabase.get(1).getTitle();

        ArrayList<Song> songWithTitle = songSearchDAO.getSongsByTitle(songTest1);

        assertTrue(songWithTitle.contains(listWithEverySongInDatabase.get(1)));

    }

    @Test
    void TestIfCorrectSongIsFoundByArtistName() {

        ArrayList<Song> listWithEverySongInDatabase = songDAO.getAllSongs();

        String artistNameOfSong = listWithEverySongInDatabase.get(1).getArtists().get(0).getName();

        ArrayList<Song> songWithTitle = songSearchDAO.getSongsByArtist(artistNameOfSong);

        assertTrue(songWithTitle.contains(listWithEverySongInDatabase.get(1)));

    }


    @Test
    void SongTitleIsEmpty() {
        assertThrows(IllegalArgumentException.class,()-> songSearchDAO.getSongsByTitle(""));
    }

    @Test
    void SongTitleIsNull() {

        assertThrows(IllegalArgumentException.class,()-> songSearchDAO.getSongsByTitle(null));
    }


    @Test
    void SongTitleNotFound() {

        assertEquals(0,songSearchDAO.getSongsByTitle(notFoundInput).size());

    }

    @Test
    void ArtistNameIsEmpty() {

        assertThrows(IllegalArgumentException.class,()-> songSearchDAO.getSongsByArtist(""));

    }

    @Test
    void ArtistNameIsNull() {

        assertThrows(IllegalArgumentException.class,()-> songSearchDAO.getSongsByArtist(null));
    }


    @Test
    void ArtistNameNotFound() {

        assertEquals(0,songSearchDAO.getSongsByArtist(notFoundInput).size());


    }



    @Test
    void AlbumTitleIsEmpty() {

        assertThrows(IllegalArgumentException.class,()-> songSearchDAO.getSongsByAlbum(""));

    }

    @Test
    void AlbumTitleIsNull() {

        assertThrows(IllegalArgumentException.class,()-> songSearchDAO.getSongsByAlbum(null));
    }


    @Test
    void AlbumTitleNotFound() {

        assertEquals(0,songSearchDAO.getSongsByAlbum(notFoundInput).size());
    }


}