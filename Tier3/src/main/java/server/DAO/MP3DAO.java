package server.DAO;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class MP3DAO implements IMP3DAO
{
  @Override public ArrayList<byte[]> getAllMP3()
  {
    ArrayList<byte[]> mp3ToReturn = new ArrayList<>();
    File folder = new File("Tier3/audio");
    File[] listOfFiles = folder.listFiles();

    for (File file : listOfFiles) {
        try
        {
          byte[] songData = Files.readAllBytes(file.toPath());
          mp3ToReturn.add(songData);
        }
        catch (IOException e)
        {
          e.printStackTrace();
        }
    }
    return mp3ToReturn;
  }
}
