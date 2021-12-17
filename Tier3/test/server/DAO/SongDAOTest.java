package server.DAO;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shared.Album;
import shared.Artist;
import shared.Song;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SongDAOTest
{
   ISongDAO songDAO;
   ArrayList<Song> songs;
   Song testSong = null;

   @BeforeEach
  void setUp() {
     songDAO = new SongDAO();
     songs = songDAO.getAllSongs();
   }

   // Vi sørger for at slette de sange der uploades ved tests
   @AfterEach
   void clear(){
     try
     {
       songDAO.removeSongFromId(testSong.getId());
     } catch (Exception e){}

   }

   @Test
  void TestSongNullThrows(){
     Song song = null;
     assertThrows(Exception.class, () ->songDAO.addNewSong(song));
   }
   /*
   One tests
    */
   @Test
   void TestSongWithRightId(){
     Song song = new Song(0, "TestSang", 420, 1920,
         new Album(0, "TestAlbum"), "testPath");
     song.addArtist(new Artist(0, "TestArtist"));
     int id = songDAO.addNewSong(song);
     System.out.println("id: " + id);
     testSong = songDAO.getSongById(id);
     System.out.println(testSong.getArtists().size());

     assertEquals(id, testSong.getId());
   }


   @Test
  void TestSongWithRightArtist(){
     Song song = new Song(0, "TestSang", 420, 1920,
         new Album(0, "TestAlbum"), "testPath");
     song.addArtist(new Artist(0, "TestArtist"));
     int id = songDAO.addNewSong(song);
     System.out.println("id: " + id);
     testSong = songDAO.getSongById(id);
     System.out.println(testSong.getArtists().size());

     assertEquals("TestArtist", testSong.getArtists().get(0).getName());
   }

  @Test
  void TestSongWithRightAlbum(){
    Song song = new Song(0, "TestSang", 420, 1920,
        new Album(0, "TestAlbum"), "testPath");
    song.addArtist(new Artist(0, "TestArtist"));
    int id = songDAO.addNewSong(song);
    System.out.println("id: " + id);
    testSong = songDAO.getSongById(id);
    System.out.println(testSong.getArtists().size());

    assertEquals("TestAlbum", testSong.getAlbum().getTitle());
  }

  @Test
  void RemoveSongRemovesASongFromDatabase(){
    Song song = new Song(0, "TestSang", 420, 1920,
        new Album(0, "TestAlbum"), "testPath");
    song.addArtist(new Artist(0, "TestArtist"));
    int id = songDAO.addNewSong(song);
    int currentSize = songDAO.getAllSongs().size();
    try
    {
      songDAO.removeSongFromId(id);
    }
    catch (NoSuchFieldException e)
    {
      e.printStackTrace();
    }
    assertEquals(currentSize-1, songDAO.getAllSongs().size());

  }

  @Test
  void RemoveSongRemovesRightSong(){
    Song song = new Song(0, "TestSang", 420, 1920,
        new Album(0, "TestAlbum"), "testPath");
    song.addArtist(new Artist(0, "TestArtist"));
    int id = songDAO.addNewSong(song);
    int currentSize = songDAO.getAllSongs().size();
    try
    {
      songDAO.removeSongFromId(id);
    }
    catch (NoSuchFieldException e)
    {
      e.printStackTrace();
    }
    assertThrows(Exception.class, ()->songDAO.removeSongFromId(id));
  }

  // Many tests

  @Test
  void TestSongWith2Artists(){
    Song song = new Song(0, "TestSang", 420, 1920,
        new Album(0, "TestAlbum"), "testPath");
    song.addArtist(new Artist(0, "TestArtist1"));
    song.addArtist(new Artist(0, "TestArtist2"));
    int id = songDAO.addNewSong(song);
    testSong = songDAO.getSongById(id);

    assertEquals(2,testSong.getArtists().size());
  }




}