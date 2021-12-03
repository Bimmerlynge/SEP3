package server.DAO;

import shared.Mp3;
import shared.Song;

import java.util.ArrayList;

public interface IMP3DAO
{
  ArrayList<Song> getAllMP3();
  byte[] getMp3(String songPath);
  void uploadMp3(Mp3 song);
}
