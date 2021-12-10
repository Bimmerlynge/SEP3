package server.DAO;
import shared.Song;

import java.util.ArrayList;

public interface ISongDAO {

    ArrayList<Song> getAllSongs();
    int addNewSong(Song newSong);
    void removeSongFromId(int songId) throws NoSuchFieldException;
    Song getSongById(int id);
}
