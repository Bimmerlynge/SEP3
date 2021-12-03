package server.DAO;
import shared.Song;

import java.util.ArrayList;

public interface ISongDAO {

    ArrayList<Song> getAllSongs();
    void postAllSongs(ArrayList<Song> songs);
    //Song getSongWithMP3(int songId);

    Song addNewSong(Song newSong);

    void removeSongFromId(int songId);
    Song getSongById(int id);
}
