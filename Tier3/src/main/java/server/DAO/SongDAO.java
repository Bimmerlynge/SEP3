package server.DAO;

import shared.Artist;
import shared.Song;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SongDAO extends BaseDAO implements ISongDAO
{
  @Override public ArrayList<Song> getAllSongs()
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement preparedStatement = connection
          .prepareStatement("SELECT * FROM Song");
      ResultSet resultSet = preparedStatement.executeQuery();

      ArrayList<Song> listOfSongs = new ArrayList<>();
      while (resultSet.next())
      {
        Song song = new Song(resultSet.getInt("songId"),
            resultSet.getString("url"), resultSet.getString("title"),
            resultSet.getInt("duration"), resultSet.getDate("releaseDate"));
        listOfSongs.add(song);
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
              resultSet.getString("url"), resultSet.getString("title"),
              resultSet.getInt("duration"), resultSet.getDate("releaseDate"));
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
