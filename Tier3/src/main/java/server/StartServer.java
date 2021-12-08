package server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import server.DAO.*;

import shared.Song;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

@SpringBootApplication public class StartServer
{
  public static void main(String[] args) throws SQLException
  {
    SpringApplication.run(StartServer.class, args);
    DriverManager.registerDriver(new org.postgresql.Driver());
    ISongDAO songDAO = new SongDAO();

    ArrayList<Song> songs = songDAO.getAllSongs();

    for (Song song : songs)
    {
      System.out.println(song.toString());
    }


  }
}
