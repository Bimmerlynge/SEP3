package server.DAO;

import shared.Mp3;
import shared.Song;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class MP3DAO implements IMP3DAO
{
  String pathDirectory = "Tier3/audio/";

  @Override public ArrayList<Song> getAllMP3()
  {
    ArrayList<Song> songsToReturn = new ArrayList<>();
    File folder = new File(pathDirectory);
    File[] listOfFiles = folder.listFiles();

    for (File file : listOfFiles) {
      String path = file.getPath();

      System.out.println(path);


    }
    return songsToReturn;
  }

  @Override public void uploadMp3(Mp3 mp3)
  {
    String path = pathDirectory + mp3.getPath();
    File file = new File(path);
    try
    {
      Files.write(Paths.get(path), mp3.getData());
      System.out.println("File was stored");
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }

  }

  @Override public byte[] getMp3(String songPath)
  {
    byte[] songData = null;
    File mp3 = new File(pathDirectory + songPath);
    try
    {

      songData = Files.readAllBytes(mp3.toPath());
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return songData;
  }
}
