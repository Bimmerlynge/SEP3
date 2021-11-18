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

  @Override public void postAllSongs(ArrayList<Song> songs)
  {

    for (Song song : songs)
    {
      try (Connection connection = getConnection())
      {
        PreparedStatement preparedStatement = connection.prepareStatement(
            "WITH data(songTitle, songDuration, songReleaseYear, MP3, albumTitle, artistName) AS (\n"
                + "    VALUES (?, ?, ?, ?, ?, ?)\n" + "),\n"
                + "     ins1 AS (\n"
                + "         INSERT INTO Song (songTitle, songDuration, songReleaseYear, MP3) SELECT songTitle, songDuration, songReleaseYear, MP3\n"
                + "                                                                          FROM data\n"
                + "             RETURNING songId\n" + "     ),\n"
                + "     ins2 AS (\n"
                + "         INSERT INTO Album (albumTitle) SELECT albumTitle FROM data RETURNING albumId\n"
                + "     ),\n" + "     ins3 AS (\n"
                + "         INSERT INTO Artist (artistName) SELECT artistName FROM data RETURNING artistId\n"
                + "     ),\n" + "     ins4 AS (\n"
                + "         INSERT INTO ArtistSongRelation (artistId, songId) SELECT ins3.artistId, ins1.songId FROM ins3, ins1\n"
                + "     ),\n" + "     ins5 AS (\n"
                + "         INSERT INTO AlbumSongRelation (albumId, songId) SELECT ins2.albumId, ins1.songId FROM ins2, ins1\n"
                + "     )\n" + "\n" + "INSERT\n"
                + "INTO AlbumArtistRelation (albumId, artistId)\n"
                + "SELECT ins2.albumId, ins3.artistId FROM ins2, ins3;");
        preparedStatement.setString(1, song.getTitle());
        preparedStatement.setInt(2, song.getDuration());
        preparedStatement.setInt(3, song.getReleaseYear());
        preparedStatement.setBytes(4, song.getMp3());
        preparedStatement.setString(5, song.getAlbumProperty().getTitle());
        preparedStatement.setString(6, song.getArtistName());
        preparedStatement.executeUpdate();
      }
      catch (SQLException throwables)
      {
        throwables.printStackTrace();
      }
    }
  }

  @Override public Song getSongWithMP3(int songId)
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement preparedStatement = connection
          .prepareStatement("SELECT * FROM AllSongs WHERE songId = ?");
      preparedStatement.setInt(1, songId);
      ResultSet resultSet = preparedStatement.executeQuery();
      Song song = null;
      while (resultSet.next())
      {
        song = new Song(resultSet.getInt("songid"),
            resultSet.getString("songtitle"), resultSet.getInt("songduration"),
            resultSet.getInt("songreleaseyear"));
        song.setMp3(resultSet.getBytes("mp3"));

        Artist artist = new Artist(resultSet.getInt("artistid"),
            resultSet.getString("artistname"));
        song.addArtist(artist);
        Album album = new Album(resultSet.getInt("albumId"),
            resultSet.getString("albumtitle"),
            resultSet.getInt("albumduration"));
        song.setAlbums(album);

      }
      return song;
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
              resultSet.getString("songtitle"),
              resultSet.getInt("songduration"),
              resultSet.getInt("songreleaseyear"));
          listOfSongs.add(song);
          songId = song.getId();
        }

        Artist artist = new Artist(resultSet.getInt("artistid"),
            resultSet.getString("artistname"));
        listOfSongs.get(listOfSongs.size() - 1).addArtist(artist);
        Album album = new Album(resultSet.getInt("albumId"),
            resultSet.getString("albumtitle"),
            resultSet.getInt("albumduration"));
        listOfSongs.get(listOfSongs.size() - 1).setAlbums(album);
      }
      return listOfSongs;

    }
    catch (SQLException throwables)
    {
      throwables.printStackTrace();
      return null;
    }

  }

  @Override public ArrayList<Song> getAllSongsWithArtists()
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
              resultSet.getString("songTitle"),
              resultSet.getInt("songDuration"),
              resultSet.getInt("songReleaseYear"));
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
