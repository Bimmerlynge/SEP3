package server.DAO;

import org.junit.jupiter.api.Test;
import shared.Song;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SongSearchDAOTest {

    ISongSearchDAO songSearchDAO = new SongSearchDAO();
    ISongDAO songDAO = new SongDAO();


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

        //String artistNameOfSong = listWithEverySongInDatabase.get(1).getArtists().get(0).getArtistName();

        //ArrayList<Song> songWithTitle = songSearchDAO.getSongsByArtist(artistNameOfSong);

        //assertTrue(songWithTitle.contains(listWithEverySongInDatabase.get(1)));

    }

    @Test
    void TestIfCorrectSongIsFoundByAlbum() {

//        ArrayList<Song> listWithEverySongInDatabase = songDAO.getAllSongs();
//
//        for (Song song : listWithEverySongInDatabase) {
//
//            if (song.getAlbums(). <= 1) {
//                String albumTitleOfSong = song.getAlbums()[0].getTitle();
//                ArrayList<Song> songWithTitle = songSearchDAO.getSongsByAlbum(albumTitleOfSong);
//                assertTrue(songWithTitle.contains(song));
//                return;
//            }
//        }
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

        assertEquals(0,songSearchDAO.getSongsByTitle("asdfasaj21nfNotASongTitle").size());


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

        assertEquals(0,songSearchDAO.getSongsByArtist("asdfasaj21nfNotAnArtistName").size());


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

        assertEquals(0,songSearchDAO.getSongsByAlbum("asdfasaj21nfNotAnAlbumName").size());


    }


}