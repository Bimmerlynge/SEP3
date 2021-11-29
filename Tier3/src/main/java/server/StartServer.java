package server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import server.DAO.*;
import shared.Song;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

@SpringBootApplication
public class StartServer
{
  public static void main(String[] args) throws SQLException {
    SpringApplication.run(StartServer.class, args);
    ISongDAO songDAO = new SongDAO();
    IAlbumDAO albumDAO = new AlbumDAO();

    DriverManager.registerDriver(new org.postgresql.Driver());


    ArrayList<Song> songs = songDAO.getAllSongs();

    IMP3DAO dao = new MP3DAO();
    dao.getAllMP3();
    System.out.println("Ud fra database");
    for (Song song : songs) {
      System.out.println(song.toString());
    }


  }
}
