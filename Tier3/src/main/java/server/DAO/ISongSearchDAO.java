package server.DAO;

import shared.Song;

import java.util.ArrayList;

public interface ISongSearchDAO {


    ArrayList<Song> getSongsByTitle(String songTitle);
    ArrayList<Song> getSongsByArtist(String artistName);
    ArrayList<Song> getSongsByAlbum(String albumTitle);


}
