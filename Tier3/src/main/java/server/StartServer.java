package server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import server.DAO.AlbumDAO;
import server.DAO.IAlbumDAO;
import server.DAO.ISongDAO;
import server.DAO.SongDAO;
import shared.Album;
import shared.Song;

import java.util.ArrayList;

@SpringBootApplication
public class StartServer
{
  public static void main(String[] args)
  {
    SpringApplication.run(StartServer.class, args);
    ISongDAO songDAO = new SongDAO();
    IAlbumDAO albumDAO = new AlbumDAO();

    ArrayList<Song> songs = songDAO.getAllSongsWithArtists();
    ArrayList<Album> albums = albumDAO.getAllAlbumsWithArtist();

    for (Song song : songs) {
      System.out.println(song.toString());
    }

    for (Album album : albums){
      System.out.println(album.toString());
    }
  }
}
