package server.DAO;

import shared.Mp3;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MP3DAO implements IMP3DAO
{
  String pathDirectory = "Tier3/audio/";

  @Override public void uploadMp3(Mp3 mp3) throws Exception {
    try
    {
      String path = pathDirectory + mp3.getPath();
      Files.write(Paths.get(path), mp3.getData());
    }
    catch (IOException e)
    {
      e.printStackTrace();
      throw new Exception(e.getMessage());
    }
  }

  @Override public byte[] getMp3(String songPath) throws Exception {
    try
    {
      File mp3 = new File(pathDirectory + songPath);
      return Files.readAllBytes(mp3.toPath());
    }
    catch (IOException e)
    {
      e.printStackTrace();
      throw new Exception(e.getMessage());
    }
  }
}
