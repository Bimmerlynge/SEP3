package server.DAO;

import shared.Album;

import java.util.ArrayList;

public interface IAlbumDAO
{
  ArrayList<Album> getAllAlbumsWithArtist();

    ArrayList<Album> searchForAlbums(String title);
}
