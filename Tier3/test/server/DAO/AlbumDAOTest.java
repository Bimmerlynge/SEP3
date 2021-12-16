package server.DAO;

import org.junit.jupiter.api.Test;
import shared.Album;
import shared.Song;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Da denne klasse minder meget om ArtistDAO har de samme struktur
 */
class AlbumDAOTest {



    private ISongDAO songDAO = new SongDAO();
    private IAlbumDAO albumDAO = new AlbumDAO();
    @Test
    void getAllAlbumsNotEmpty(){
        Album newAlbum = new Album(0, "newAlbum");
        createSongWithAlbum(newAlbum);

        ArrayList<Album> getAllAlbums = albumDAO.getAllAlbums();

        assertTrue(0 < getAllAlbums.size());

    }

    @Test
    void getAllAlbumsContainsNewAlbum(){
        Album newAlbum = new Album(0, "newAlbumWithThisSpecificTitle");
        createSongWithAlbum(newAlbum);

        ArrayList<Album> getAllAlbums = albumDAO.getAllAlbums();
        boolean toAssert = false;
        for (Album album : getAllAlbums) {
            if (newAlbum.getTitle().equals(album.getTitle())){
                toAssert = true;
                break;
            }
        }
        assertTrue(toAssert);
    }



    @Test
    void getAlbumCorrectWhenSearchingForIt(){
        String titleOfThisAlbum = "ThisIsTheAlbumBeingSearchedForVerySpecificNameSoNotFindingAnythingWeDontWant";
        Album newAlbum = new Album(0, titleOfThisAlbum);
        createSongWithAlbum(newAlbum);

        ArrayList<Album> searchForAlbums = albumDAO.searchForAlbums(titleOfThisAlbum);

        for (Album album : searchForAlbums) {
            if (newAlbum.getTitle().equals(album.getTitle())){
                assertEquals(newAlbum.getTitle(), album.getTitle());
                return;
            }
        }
        fail();


    }

    @Test
    void ifMultipleSongsFillParametersBothReturned(){

        String baseTitle = "titleOfAlbum";
        Album newAlbum = new Album(0, baseTitle);
        createSongWithAlbum(newAlbum);

        Album otherAlbum = new Album(0, baseTitle + "ButMoreSpecific");
        createSongWithAlbum(otherAlbum);

        ArrayList<Album> searchForAlbums = albumDAO.searchForAlbums(baseTitle);

        assertTrue(1 < searchForAlbums.size());
    }

    /**
     * Her behøver der ikke være noget i databasen
     */
    @Test
    void searchForAlbumWithEmptyString(){

        assertThrows(IllegalArgumentException.class, () -> albumDAO.searchForAlbums(""));

    }
    @Test
    void searchForAlbumWithNull(){

        assertThrows(IllegalArgumentException.class, () -> albumDAO.searchForAlbums(null));

    }

    /**
     * Her ligges der først en sang i databasen da dette sørger for at det album bliver lagt i databasen
     */
    private void createSongWithAlbum(Album newAlbum) {
        Song newSong = new Song(0, "NewSong", 300, 2021, newAlbum, null);
        songDAO.addNewSong(newSong);
    }

}