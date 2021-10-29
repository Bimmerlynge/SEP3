package server.DAO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.expression.spel.ast.InlineList;
import org.springframework.util.Assert;
import shared.Song;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SongDAOTest
{
   ISongDAO songDAO;
   ArrayList<Song> songs;

   @BeforeEach
  void setUp() {
     songDAO = new SongDAO();
     songs = songDAO.getAllSongs();
   }

   @Test
  void TestIfListIsNull() {
     assertNotNull(songs);
   }

   @Test
  void TestIfCorrectSongsIsAddedToList() {
     assertEquals(3, songs.size());
     assertEquals("Ring_Of_Fire", songs.get(0).getTitle());
     assertEquals("Champion", songs.get(1).getTitle());
     assertEquals("Under_The_Bridge", songs.get(2).getTitle());
   }
}