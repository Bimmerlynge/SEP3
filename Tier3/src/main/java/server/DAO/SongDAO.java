package server.DAO;

import shared.Album;
import shared.Artist;
import shared.Song;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SongDAO extends BaseDAO implements ISongDAO
{
  @Override public ArrayList<Song> getSongsByFilter(String type, String parameter) throws Exception {

    String typeString ="";
    if (type.equals("Title"))
      typeString = "songTitle";
    else if (type.equals("Artist"))
      typeString = "artistname";
    else if (type.equals("Album"))
      typeString = "albumtitle";
    else
      throw new Exception("Not valid Type");

    System.out.println("typeString: " + typeString);
    try (Connection connection = getConnection())
    {
      PreparedStatement preparedStatement = connection
          .prepareStatement("SELECT * FROM AllSongs WHERE " + typeString + " = ?");

      preparedStatement.setString(1,parameter);
      ResultSet resultSet = preparedStatement.executeQuery();

      ArrayList<Song> listOfSongs = new ArrayList<>();
      int songId = 0;

      while (resultSet.next())
      {
        System.out.println("Inde i loop");
        if (songId != resultSet.getInt("songid"))
        {
          Song song = new Song(resultSet.getInt("songid"),
              resultSet.getString("url"), resultSet.getString("songtitle"),
              resultSet.getInt("songduration"), resultSet.getDate("songreleasedate"));
          listOfSongs.add(song);
          songId = song.getId();
        }

        Artist artist = new Artist(resultSet.getInt("artistid"),
            resultSet.getString("artistname"));
        listOfSongs.get(listOfSongs.size() - 1).addArtist(artist);
        Album album = new Album(resultSet.getInt("albumId"), resultSet.getString("albumtitle"),
            resultSet.getInt("albumduration"),
            resultSet.getDate("albumreleasedate"));
        listOfSongs.get(listOfSongs.size()-1).addAlbum(album);
      }
      return listOfSongs;

    }
    catch (SQLException throwables)
    {
      throwables.printStackTrace();
      return null;
    }



  }

  @Override public ArrayList<Song> getAllSongs()
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement preparedStatement = connection
          .prepareStatement("SELECT * FROM AllSongs");
      ResultSet resultSet = preparedStatement.executeQuery();

      ArrayList<Song> listOfSongs = new ArrayList<>();
      int songId = 0;

      while (resultSet.next())
      {
        if (songId != resultSet.getInt("songid"))
        {
          Song song = new Song(resultSet.getInt("songid"),
              resultSet.getString("url"), resultSet.getString("songtitle"),
              resultSet.getInt("songduration"), resultSet.getDate("songreleasedate"));
          listOfSongs.add(song);
          songId = song.getId();
        }

        Artist artist = new Artist(resultSet.getInt("artistid"),
            resultSet.getString("artistname"));
        listOfSongs.get(listOfSongs.size() - 1).addArtist(artist);
        Album album = new Album(resultSet.getInt("albumId"), resultSet.getString("albumtitle"),
            resultSet.getInt("albumduration"),
            resultSet.getDate("albumreleasedate"));
        listOfSongs.get(listOfSongs.size()-1).addAlbum(album);
      }
      return listOfSongs;

    }
    catch (SQLException throwables)
    {
      throwables.printStackTrace();
      return null;
    }

  }

  @Override
  public ArrayList<Song> getAllSongsWithArtists()
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement preparedStatement = connection
          .prepareStatement("SELECT * FROM SongWithArtist");
      ResultSet resultSet = preparedStatement.executeQuery();

      ArrayList<Song> listOfSongs = new ArrayList<>();
      int songId = 0;

      while (resultSet.next())
      {
        if (songId != resultSet.getInt("songId"))
        {
          Song song = new Song(resultSet.getInt("songId"),
              resultSet.getString("url"), resultSet.getString("songTitle"),
              resultSet.getInt("songDuration"), resultSet.getDate("songReleaseDate"));
          listOfSongs.add(song);
          songId = song.getId();
        }

        Artist artist = new Artist(resultSet.getInt("artistid"),
            resultSet.getString("artistname"));
        listOfSongs.get(listOfSongs.size() - 1).addArtist(artist);
      }
      return listOfSongs;

    }
    catch (SQLException throwables)
    {
      throwables.printStackTrace();
      return null;
    }

  }
}
