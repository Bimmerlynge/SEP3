package server.DAO;
import shared.Song;

import java.util.ArrayList;

public interface ISongDAO {

    ArrayList<Song> getAllSongs();
    ArrayList<Song> getAllSongsWithArtists();
    void postAllSongs(ArrayList<Song> songs);
    Song getSongWithMP3(int songId);
}
