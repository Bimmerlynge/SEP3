package server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import server.DAO.ISongDAO;
import server.DAO.SongDAO;
import shared.ISong;

import java.util.ArrayList;

@SpringBootApplication
public class StartServer
{
  public static void main(String[] args)
  {
    SpringApplication.run(StartServer.class, args);
    ISongDAO songDAO = new SongDAO();

    ArrayList<ISong> songs = songDAO.getAllSongs();

    for (ISong song : songs) {
      System.out.println(song.toString());
    }
  }
}
