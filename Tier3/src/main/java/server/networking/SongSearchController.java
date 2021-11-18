package server.networking;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import server.DAO.ISongSearchDAO;
import server.DAO.SongSearchDAO;
import shared.Song;

import java.util.ArrayList;


@RestController

public class SongSearchController {
    ISongSearchDAO songSearchDAO = new SongSearchDAO();


    @GetMapping("/songSearch/songTitle={songTitle}")
    public synchronized String getSongsBySongTitle(@PathVariable String songTitle) {
        System.out.println("songTitle: " + songTitle);
        ArrayList<Song> songs = songSearchDAO.getSongsByTitle(songTitle);
        System.out.println("Sender list, size: " + songs.size());
        return new Gson().toJson(songs);
    }

    @GetMapping("/songSearch/artistName={artistName}")
    public synchronized String getSongsByArtistName(@PathVariable String artistName) {
        System.out.println("artistName : " + artistName );
        ArrayList<Song> songs = songSearchDAO.getSongsByArtist(artistName);
        System.out.println("Sender list, size: " + songs.size());
        return new Gson().toJson(songs);
    }

    @GetMapping("/songSearch/albumTitle={albumTitle}")
    public synchronized String getSongsByAlbumTitle(@PathVariable String albumTitle) {
        System.out.println("albumTitle: " + albumTitle);
        ArrayList<Song> songs = songSearchDAO.getSongsByAlbum(albumTitle);
        System.out.println("Sender list, size: " + songs.size());
        return new Gson().toJson(songs);
    }


}
