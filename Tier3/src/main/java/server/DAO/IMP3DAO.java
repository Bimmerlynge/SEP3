package server.DAO;

import shared.Mp3;


public interface IMP3DAO
{
  byte[] getMp3(String songPath) throws Exception;
  void uploadMp3(Mp3 song) throws Exception;
}
