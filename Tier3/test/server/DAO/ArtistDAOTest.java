package server.DAO;

import org.junit.jupiter.api.Test;
import shared.Album;
import shared.Artist;
import shared.Song;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Da denne klasse minder meget op AlbumDAO har de samme struktur
 */
class ArtistDAOTest {


    private ISongDAO songDAO = new SongDAO();
    private IArtistDAO artistDAO = new ArtistDAO();
    @Test
    void getAllArtistsNotEmpty(){
        Artist newArtist = new Artist(0, "newArtist");
        ArrayList<Artist> artistsForSong = new ArrayList<>();
        artistsForSong.add(newArtist);
        createSongWithArtists(artistsForSong);

        ArrayList<Artist> getAllArtists = artistDAO.getAllArtist();

        assertTrue(0 < getAllArtists.size());

    }

    @Test
    void getAllArtistsContainsNewArtist(){

        Artist newArtist = new Artist(0, "newArtistWithThisSpecificName");
        ArrayList<Artist> artistsForSong = new ArrayList<>();
        artistsForSong.add(newArtist);
        createSongWithArtists(artistsForSong);


        ArrayList<Artist> getAllArtists = artistDAO.getAllArtist();
        boolean toAssert = false;
        for (Artist artist : getAllArtists) {
            if (artist.getName().equals(newArtist.getName())){
                toAssert = true;
                break;
            }
        }
        assertTrue(toAssert);
    }



    @Test
    void getAlbumCorrectWhenSearchingForIt(){

        String artistName = "newArtistWithThisSpecificNameThisWillBeSearchedFor";
        Artist newArtist = new Artist(0, artistName);
        ArrayList<Artist> artistsForSong = new ArrayList<>();
        artistsForSong.add(newArtist);
        createSongWithArtists(artistsForSong);


        ArrayList<Artist> searchForArtists = artistDAO.searchForArtists(artistName);

        for (Artist artist : searchForArtists) {
            if (newArtist.getName().equals(artist.getName())){
                assertEquals(newArtist.getName(), artist.getName());
                return;
            }
        }
        fail();

    }

    @Test
    void ifMultipleSongsFillParametersBothReturned(){

        String baseName = "NameOfPerson";
        Artist newArtist = new Artist(0, baseName);
        Artist otherArtist = new Artist(0, baseName + "ButMoreSpecific");
        ArrayList<Artist> artistsForSong = new ArrayList<>();
        artistsForSong.add(newArtist);
        artistsForSong.add(otherArtist);
        createSongWithArtists(artistsForSong);


        ArrayList<Artist> searchForArtist = artistDAO.searchForArtists(baseName);

        assertTrue(1 < searchForArtist.size());
    }

    /**
     * Her behøver der ikke være noget i databasen
     */
    @Test
    void searchForAlbumWithEmptyString(){

        assertThrows(IllegalArgumentException.class, () -> artistDAO.searchForArtists(""));

    }
    @Test
    void searchForAlbumWithNull(){

        assertThrows(IllegalArgumentException.class, () -> artistDAO.searchForArtists(null));

    }

    /**
     * Her ligges der først en sang i databasen da dette sørger for at der bliver oprettet artister i databasen
     */
    private void createSongWithArtists(ArrayList<Artist> allWantedArtists) {
        Album newAlbum = new Album(0, "newAlbumTitle");
        Song newSong = new Song(0, "NewSong", 300, 2021, newAlbum, null);

        for (Artist artist : allWantedArtists) {
            newSong.addArtist(artist);
        }
        songDAO.addNewSong(newSong);
    }

}