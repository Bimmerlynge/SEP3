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

        String artistNameOfSong = listWithEverySongInDatabase.get(1).getArtists().get(0).getArtistName();

        ArrayList<Song> songWithTitle = songSearchDAO.getSongsByArtist(artistNameOfSong);

        assertTrue(songWithTitle.contains(listWithEverySongInDatabase.get(1)));

    }

    @Test
    void TestIfCorrectSongIsFoundByAlbum() {

        ArrayList<Song> listWithEverySongInDatabase = songDAO.getAllSongs();

        for (Song song : listWithEverySongInDatabase) {

            if (song.getAlbums().size() <= 1) {
                String albumTitleOfSong = song.getAlbums().get(0).getTitle();
                ArrayList<Song> songWithTitle = songSearchDAO.getSongsByAlbum(albumTitleOfSong);
                assertTrue(songWithTitle.contains(song));
                return;
            }
        }
    }




    //TODO

    @Test
    void TitleIsEmpty() {

        ArrayList<Song> listWithEverySongInDatabase = songDAO.getAllSongs();

        String songTest1 = listWithEverySongInDatabase.get(1).getTitle();

        ArrayList<Song> songWithTitle = songSearchDAO.getSongsByTitle(songTest1);

        assertTrue(songWithTitle.contains(listWithEverySongInDatabase.get(1)));
        //TODO

    }

    @Test
    void TitleIsNull() {

        ArrayList<Song> listWithEverySongInDatabase = songDAO.getAllSongs();

        String songTest1 = listWithEverySongInDatabase.get(1).getTitle();

        ArrayList<Song> songWithTitle = songSearchDAO.getSongsByTitle(songTest1);

        assertTrue(songWithTitle.contains(listWithEverySongInDatabase.get(1)));
        //TODO

    }

    @Test
    void TitleNotFound() {

        ArrayList<Song> listWithEverySongInDatabase = songDAO.getAllSongs();

        String songTest1 = listWithEverySongInDatabase.get(1).getTitle();

        ArrayList<Song> songWithTitle = songSearchDAO.getSongsByTitle(songTest1);

        assertTrue(songWithTitle.contains(listWithEverySongInDatabase.get(1)));

        //TODO

    }



}