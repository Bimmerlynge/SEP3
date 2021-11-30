package server.util;

import shared.Song;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ByteConverter
{
  public static byte[] convertToBytes(Song song) throws IOException
  {
    try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos)) {
      out.writeObject(song);
      return bos.toByteArray();
    }
  }
}
